package cn.com.common.convert;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 默认的类型转换器<br />
 * 支持java基础类型、日期类型、数组类型、集合类型、映射类型的转换<br/>
 * （注意：本类非线程安全）
 * 
 * @author wuliwei
 * 
 */
public class DefaultConverter {
	protected Map<Class<?>, List<Method>> readMethodsCache = new HashMap<Class<?>, List<Method>>();
	protected Map<Class<?>, List<Method>> writeMethodsCache = new HashMap<Class<?>, List<Method>>();
	protected Map<Class<?>, Class<?>> classesMap = new HashMap<Class<?>, Class<?>>();
	protected Class<?> arrayDestClass = null;

	/**
	 * 注册类型映射关系<br />
	 * 对于注册中的类型srcClass，将被统一转换成destClass <br />
	 * 必须避免注册中的类型转换关系出现闭环，如：A -> B -> C -> A <br />
	 * 如果确实有这样的场景，请在转换时使用不同的实例对象即可
	 * 
	 * @param srcClass
	 * @param destClass
	 * @return
	 */
	public DefaultConverter registClassMap(Class<?> srcClass, Class<?> destClass) {
		classesMap.put(srcClass, destClass);
		return this;
	}

	/**
	 * 注册数组类型映射关系<br />
	 * 数组类型比较特殊，无法用固定的class类型来标识，<br />
	 * 对于需要将所有数组类型统一转换成destClass，可以该方法来实现 <br />
	 * 优先级低于通过registClassMap注册的类型映射关系
	 * 
	 * @param destClass
	 * @return
	 */
	public DefaultConverter registArrayClassMap(Class<?> destClass) {
		arrayDestClass = destClass;
		return this;
	}

	public Object convert(Object o, Class<?> destClass) {
		return convert(o, destClass, null);
	}

	public Object convert(Object o, Class<?> destClass, Class<?>[] cts) {
		if (null == o) {
			return null;
		}
		Object dest = null;
		Class<?> srcClass = o.getClass();
		destClass = trans(srcClass, destClass);
		if (isSkip(srcClass, destClass, cts)) {
			return o;
		} else if (isPrimitive(srcClass) && isPrimitive(destClass)
				&& null != (dest = convertPrimitive(o, destClass))) {
			return dest;
		} else if (isDate(srcClass) && isDate(destClass)
				&& null != (dest = convertDate(o, destClass))) {
			return dest;
		} else if (isCollection(srcClass)
				&& null != (dest = convertCollection(o, destClass, cts))) {
			return dest;
		} else if (isMap(srcClass)
				&& null != (dest = convertMap(o, destClass, cts))) {
			return dest;
		} else if (isArray(srcClass)
				&& null != (dest = convertArray(o, destClass, cts))) {
			return dest;
		} else if (null != (dest = convertObject(o, destClass, cts))) {
			return dest;
		} else if (destClass.isAssignableFrom(srcClass) && null == cts) {
			return o;
		}
		return null;
	}

	/**
	 * 符合要求的类型跳过转换
	 * 
	 * @param srcClass
	 * @param destClass
	 * @param cts
	 * @return
	 */
	protected boolean isSkip(Class<?> srcClass, Class<?> destClass,
			Class<?>[] cts) {
		return false;
	}

	/**
	 * 数值、字符基础类型转换
	 * 
	 * @param o
	 * @param destClass
	 * @return
	 */
	protected Object convertPrimitive(Object o, Class<?> destClass) {
		Class<?> srcClass = o.getClass();
		if (isPrimitiveEq(srcClass, destClass)) {
			return o;
		} else if (isString(destClass)) {
			return o.toString();
		} else if (isString(srcClass)) {
			String s = (String) o;
			if (isCharacter(destClass) && s.length() == 1) {
				return convert(s.charAt(0), destClass);
			} else if (isFloat(destClass)) {
				return convert(Float.parseFloat(s), destClass);
			} else if (isLong(destClass)) {
				return convert(Long.parseLong(s), destClass);
			} else if (isDouble(destClass)) {
				return convert(Double.parseDouble(s), destClass);
			}
			return convert(Integer.parseInt(s), destClass);
		} else if (isBoolean(srcClass)) {
			if ((Boolean) o) {
				return convert(new Integer(1), destClass);
			} else {
				return convert(new Integer(0), destClass);
			}
		} else if (isByte(srcClass)) {
			return convert(((Byte) o).intValue(), destClass);
		} else if (isCharacter(srcClass)) {
			return convert((int) ((Character) o), destClass);
		} else if (isShort(srcClass)) {
			return convert(((Short) o).intValue(), destClass);
		} else if (isInteger(srcClass)) {
			if (isBoolean(destClass)) {
				if (((Integer) o).intValue() == 1) {
					return convert(true, destClass);
				} else if (((Integer) o).intValue() == 0) {
					return convert(false, destClass);
				}
				return null;
			} else if (isByte(destClass)) {
				if (((Integer) o).intValue() > Byte.MAX_VALUE) {
					return null;
				}
				return convert(((Integer) o).byteValue(), destClass);
			} else if (isCharacter(destClass)) {
				if (((Integer) o).intValue() > Character.MAX_VALUE) {
					return null;
				}
				char ch = (char) ((Integer) o).intValue();
				return convert(ch, destClass);
			} else if (isShort(destClass)) {
				if (((Integer) o).intValue() > Short.MAX_VALUE) {
					return null;
				}
				return convert(((Integer) o).shortValue(), destClass);
			} else if (isFloat(destClass)) {
				return convert(((Integer) o).floatValue(), destClass);
			}
			return convert(((Integer) o).longValue(), destClass);
		} else if (isLong(srcClass)) {
			if (isDouble(destClass)) {
				return convert(((Long) o).doubleValue(), destClass);
			}
			if (((Long) o).longValue() > Integer.MAX_VALUE) {
				return null;
			}
			return convert(((Long) o).intValue(), destClass);
		} else if (isFloat(srcClass)) {
			if (isDouble(destClass)) {
				return convert(Double.valueOf(Float.toString((Float) o)),
						destClass);
			}
			return convert(((Float) o).intValue(), destClass);
		} else if (isDouble(srcClass)) {
			if (isFloat(destClass)) {
				if (((Double) o).doubleValue() > Float.MAX_VALUE) {
					return null;
				}
				return convert(((Double) o).floatValue(), destClass);
			}
			return convert(((Double) o).longValue(), destClass);
		}
		return null;
	}

	/**
	 * 日期转换
	 * 
	 * @param o
	 * @param destClass
	 * @return
	 */
	protected Object convertDate(Object o, Class<?> destClass) {
		if (o.getClass() == destClass) {
			return o;
		} else if (destClass == java.sql.Date.class) {
			return convert(new java.sql.Date(((java.util.Date) o).getTime()),
					destClass);
		} else if (destClass == java.sql.Timestamp.class) {
			return convert(
					new java.sql.Timestamp(((java.util.Date) o).getTime()),
					destClass);
		} else if (destClass == java.util.Date.class) {
			return convert(new java.util.Date(((java.util.Date) o).getTime()),
					destClass);
		}
		return null;
	}

	/**
	 * 集合转换
	 * 
	 * @param o
	 * @param destClass
	 * @param cts
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected Object convertCollection(Object o, Class<?> destClass,
			Class<?>[] cts) {
		if (isCollection(destClass)) {
			Collection src = ((Collection) o);
			Iterator iterator = src.iterator();
			Collection coll;
			try {
				coll = (Collection) destClass.newInstance();
			} catch (Exception e) {
				if (Set.class.isAssignableFrom(destClass)) {
					coll = new HashSet();
				} else {
					coll = new ArrayList();
				}
			}
			Object obj;
			while (iterator.hasNext()) {
				obj = iterator.next();
				if (null == obj) {
					destClass = null;
				} else if (null == cts) {
					destClass = obj.getClass();
				} else {
					destClass = cts[0];
				}
				coll.add(convert(obj, destClass));
			}
			return coll;
		} else if (destClass.isArray()) {
			return convertCollection2Array(o, destClass, cts);
		}
		return null;
	}

	/**
	 * 集合转换成数组
	 * 
	 * @param o
	 * @param destClass
	 * @param cts
	 * @return
	 */
	@SuppressWarnings({ "rawtypes" })
	protected Object convertCollection2Array(Object o, Class<?> destClass,
			Class<?>[] cts) {
		if (null == cts) {
			cts = new Class<?>[] { destClass.getComponentType() };
		}
		Collection src = ((Collection) o);
		Iterator iterator = src.iterator();
		Object dest = null;
		Object obj;
		int i = 0;
		while (iterator.hasNext()) {
			obj = convert(iterator.next(), cts[0]);
			if (null != obj && null == dest) {
				// 由第一个非空元素决定数组的元素类型
				dest = Array.newInstance(obj.getClass(), src.size());
			}
			Array.set(dest, i++, obj);
		}
		if (null == dest) {
			dest = Array.newInstance(cts[0], src.size());
		}
		return dest;
	}

	/**
	 * 映射转换
	 * 
	 * @param o
	 * @param destClass
	 * @param cts
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected Object convertMap(Object o, Class<?> destClass, Class<?>[] cts) {
		if (isMap(destClass)) {
			Map src = (Map) o;
			Map dest;
			try {
				dest = (Map) destClass.newInstance();
			} catch (Exception e) {
				dest = new HashMap();
			}
			Object obj;
			Class<?> kc, vc;
			for (Object k : src.keySet()) {
				obj = src.get(k);
				if (null == cts) {
					if (null == k) {
						kc = null;
					} else {
						kc = k.getClass();
					}
					if (null == obj) {
						vc = null;
					} else {
						vc = obj.getClass();
					}
				} else {
					kc = cts[0];
					vc = cts[1];
				}
				dest.put(convert(k, kc), convert(obj, vc));
			}
			return dest;
		}
		return convertMap2Object(o, destClass, cts);
	}

	/**
	 * 映射转换成对象
	 * 
	 * @param o
	 * @param destClass
	 * @param cts
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	protected Object convertMap2Object(Object o, Class<?> destClass,
			Class<?>[] cts) {
		Object dest;
		try {
			dest = destClass.newInstance();
		} catch (Exception e) {
			return null;
		}
		Map src = (Map) o;
		List<Method> methods = getWriteMethodsWithCache(destClass);
		Object value = null;
		for (Method m : methods) {
			try {
				value = src.get(trans(m.getName()));
				if (null == value) {
					continue;
				}
				destClass = getParameterType(m, 0);
				cts = getComponentType(m, 0);
				value = convert(value, destClass, cts);
				if (null == value) {
					continue;
				}
				m.invoke(dest, value);
			} catch (Exception e) {
				return null;
			}
		}
		return dest;
	}

	/**
	 * 数组转换
	 * 
	 * @param o
	 * @param destClass
	 * @param cts
	 * @return
	 */
	protected Object convertArray(Object o, Class<?> destClass, Class<?>[] cts) {
		int len = Array.getLength(o);
		if (destClass.isArray()) {
			if (null == cts) {
				cts = new Class<?>[] { o.getClass().getComponentType() };
			}
			Object dest = null;
			Object obj;
			for (int i = 0; i < len; i++) {
				obj = convert(Array.get(o, i), cts[0]);
				if (null != obj && null == dest) {
					// 由第一个非空元素决定数组的元素类型
					dest = Array.newInstance(obj.getClass(), len);
				}
				Array.set(dest, i, obj);
			}
			if (null == dest) {
				dest = Array.newInstance(cts[0], len);
			}
			return dest;
		} else if (isCollection(destClass)) {
			return convertArray2Collection(o, destClass, cts);
		}
		return null;
	}

	/**
	 * 数组转换成集合
	 * 
	 * @param o
	 * @param destClass
	 * @param cts
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected Object convertArray2Collection(Object o, Class<?> destClass,
			Class<?>[] cts) {
		int len = Array.getLength(o);
		Collection coll;
		try {
			coll = (Collection) destClass.newInstance();
		} catch (Exception e) {
			if (Set.class.isAssignableFrom(destClass)) {
				coll = new HashSet();
			} else {
				coll = new ArrayList();
			}
		}
		Object obj;
		for (int i = 0; i < len; i++) {
			obj = Array.get(o, i);
			if (null == obj) {
				destClass = null;
			} else if (null == cts) {
				destClass = obj.getClass();
			} else {
				destClass = cts[0];
			}
			coll.add(convert(obj, destClass));
		}
		return coll;
	}

	/**
	 * 对象转换
	 * 
	 * @param o
	 * @param destClass
	 * @param cts
	 * @return
	 */
	protected Object convertObject(Object o, Class<?> destClass, Class<?>[] cts) {
		if (isMap(destClass)) {
			return convertObject2Map(o, destClass, cts);
		}
		Object dest;
		try {
			dest = destClass.newInstance();
		} catch (Exception e) {
			return null;
		}
		Class<?> srcClass = o.getClass();
		List<Method> readMethods = getReadMethodsWithCache(srcClass);
		List<Method> writeMethods = getWriteMethodsWithCache(destClass);
		Map<String, Method> mapMethods = new HashMap<String, Method>();
		for (Method m : writeMethods) {
			mapMethods.put(trans(m.getName()), m);
		}
		Object value = null;
		Method writeMethod;
		for (Method readMethod : readMethods) {
			try {
				writeMethod = mapMethods.get(trans(readMethod.getName()));
				if (null == writeMethod) {
					continue;
				}
				value = readMethod.invoke(o);
				if (null == value) {
					continue;
				}
				destClass = getParameterType(writeMethod, 0);
				cts = getComponentType(writeMethod, 0);
				value = convert(value, destClass, cts);
				if (null == value) {
					continue;
				}
				writeMethod.invoke(dest, value);
			} catch (Exception e) {
				return null;
			}
		}
		return dest;
	}

	/**
	 * 对象转换成映射
	 * 
	 * @param o
	 * @param destClass
	 * @param cts
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected Object convertObject2Map(Object o, Class<?> destClass,
			Class<?>[] cts) {
		Map dest;
		try {
			dest = (Map) destClass.newInstance();
		} catch (Exception e) {
			dest = new HashMap();
		}
		Class<?> srcClass = o.getClass();
		List<Method> methods = getReadMethodsWithCache(srcClass);
		String k;
		Object obj;
		Class<?> kc, vc;
		for (Method m : methods) {
			try {
				k = trans(m.getName());
				obj = m.invoke(o);
				if (null == cts) {
					if (null == k) {
						kc = null;
					} else {
						kc = k.getClass();
					}
					if (null == obj) {
						vc = null;
					} else {
						vc = obj.getClass();
					}
				} else {
					kc = cts[0];
					vc = cts[1];
				}
				dest.put(convert(k, kc), convert(obj, vc));
			} catch (Exception e) {
				return null;
			}
		}
		return dest;
	}

	/**
	 * 判断是否为基础类型
	 * 
	 * @param srcClass
	 * @return
	 */
	protected boolean isPrimitive(Class<?> srcClass) {
		return isBoolean(srcClass) || isByte(srcClass) || isCharacter(srcClass)
				|| isShort(srcClass) || isInteger(srcClass) || isLong(srcClass)
				|| isFloat(srcClass) || isDouble(srcClass)
				|| isString(srcClass);
	}

	/**
	 * 判断是否为相同的基础类型
	 * 
	 * @param srcClass
	 * @param destClass
	 * @return
	 */
	protected boolean isPrimitiveEq(Class<?> srcClass, Class<?> destClass) {
		if (isBoolean(srcClass) && isBoolean(destClass)) {
			return true;
		} else if (isByte(srcClass) && isByte(destClass)) {
			return true;
		} else if (isCharacter(srcClass) && isCharacter(destClass)) {
			return true;
		} else if (isShort(srcClass) && isShort(destClass)) {
			return true;
		} else if (isInteger(srcClass) && isInteger(destClass)) {
			return true;
		} else if (isFloat(srcClass) && isFloat(destClass)) {
			return true;
		} else if (isLong(srcClass) && isLong(destClass)) {
			return true;
		} else if (isDouble(srcClass) && isDouble(destClass)) {
			return true;
		} else if (isString(srcClass) && isString(destClass)) {
			return true;
		}
		return false;
	}

	protected boolean isBoolean(Class<?> srcClass) {
		return srcClass == Boolean.TYPE || srcClass == Boolean.class;
	}

	protected boolean isByte(Class<?> srcClass) {
		return srcClass == Byte.TYPE || srcClass == Byte.class;
	}

	protected boolean isCharacter(Class<?> srcClass) {
		return srcClass == Character.TYPE || srcClass == Character.class;
	}

	protected boolean isShort(Class<?> srcClass) {
		return srcClass == Short.TYPE || srcClass == Short.class;
	}

	protected boolean isInteger(Class<?> srcClass) {
		return srcClass == Integer.TYPE || srcClass == Integer.class;
	}

	protected boolean isFloat(Class<?> srcClass) {
		return srcClass == Float.TYPE || srcClass == Float.class;
	}

	protected boolean isLong(Class<?> srcClass) {
		return srcClass == Long.TYPE || srcClass == Long.class;
	}

	protected boolean isDouble(Class<?> srcClass) {
		return srcClass == Double.TYPE || srcClass == Double.class;
	}

	protected boolean isString(Class<?> srcClass) {
		return srcClass == String.class;
	}

	protected boolean isDate(Class<?> srcClass) {
		return srcClass == java.util.Date.class
				|| srcClass == java.sql.Date.class
				|| srcClass == java.sql.Timestamp.class;
	}

	protected boolean isCollection(Class<?> srcClass) {
		return Collection.class.isAssignableFrom(srcClass);
	}

	protected boolean isMap(Class<?> srcClass) {
		return Map.class.isAssignableFrom(srcClass);
	}

	protected boolean isArray(Class<?> srcClass) {
		return srcClass.isArray();
	}

	protected List<Method> getReadMethodsWithCache(Class<?> c) {
		List<Method> methods = readMethodsCache.get(c);
		if (null == methods) {
			methods = getReadMethods(c);
			readMethodsCache.put(c, methods);
		}
		return methods;
	}

	protected List<Method> getReadMethods(Class<?> c) {
		List<Method> methods = new ArrayList<Method>();
		for (Method m : c.getMethods()) {
			if (m.getName().startsWith("get")
					&& m.getParameterTypes().length == 0
					&& Modifier.isPublic(m.getModifiers())
					&& !Modifier.isStatic(m.getModifiers())
					&& !m.getName().equals("getClass")) {
				m.setAccessible(true);
				methods.add(m);
			}
		}
		return methods;
	}

	protected List<Method> getWriteMethodsWithCache(Class<?> c) {
		List<Method> methods = writeMethodsCache.get(c);
		if (null == methods) {
			methods = getWriteMethods(c);
			writeMethodsCache.put(c, methods);
		}
		return methods;
	}

	protected List<Method> getWriteMethods(Class<?> c) {
		List<Method> methods = new ArrayList<Method>();
		for (Method m : c.getMethods()) {
			if (m.getName().startsWith("set")
					&& m.getParameterTypes().length == 1
					&& Modifier.isPublic(m.getModifiers())
					&& !Modifier.isStatic(m.getModifiers())) {
				m.setAccessible(true);
				methods.add(m);
			}
		}
		return methods;
	}

	protected Class<?> trans(Class<?> srcClass, Class<?> destClass) {
		if (classesMap.containsKey(srcClass)) {
			destClass = classesMap.get(srcClass);
		} else if (null != arrayDestClass && isArray(srcClass)) {
			destClass = arrayDestClass;
		}
		return destClass;
	}

	/**
	 * 根据get、set方法名转成对应的属性名
	 * 
	 * @param name
	 * @return
	 */
	protected String trans(String name) {
		name = name.substring(3);
		if (name.length() > 1) {
			name = name.substring(0, 1).toLowerCase() + name.substring(1);
		} else {
			name = name.toLowerCase();
		}
		return name;
	}

	public static Class<?> getParameterType(Method m, int index) {
		Class<?>[] cs = m.getParameterTypes();
		if (null == cs || cs.length == 0) {
			return null;
		}
		return cs[index];
	}

	public static Class<?>[] getComponentType(Method m, int index) {
		Class<?>[] cs = m.getParameterTypes();
		if (null == cs || cs.length == 0) {
			return null;
		}
		Class<?> pt = cs[index];
		if (pt.isArray()) {
			return new Class<?>[] { pt.getComponentType() };
		} else if (Collection.class.isAssignableFrom(pt)
				|| Map.class.isAssignableFrom(pt)) {
			Type[] ts = m.getGenericParameterTypes();
			Type temp = ts[index];
			if (ParameterizedType.class.isAssignableFrom(temp.getClass())) {
				ParameterizedType pit = (ParameterizedType) temp;
				Type[] ats = pit.getActualTypeArguments();
				if (null == ats || ats.length == 0) {
					return null;
				}
				Class<?>[] cts = new Class<?>[ats.length];
				for (int i = 0; i < ats.length; i++) {
					cts[i] = (Class<?>) ats[i];
				}
				return cts;
			}
		}
		return null;
	}

}
