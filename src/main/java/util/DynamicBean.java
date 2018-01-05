package util;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import net.sf.cglib.beans.BeanGenerator;
import net.sf.cglib.beans.BeanMap;

public class DynamicBean {
	/**
	 * 实体Object
	 */
	private Object obeject = null;

	/**
	 * 属性map
	 */
	private BeanMap beanMap = null;

	public DynamicBean() {
		super();
	}

	@SuppressWarnings("rawtypes")
	public DynamicBean(Map propertyMap) {
		this.obeject = generateBean(propertyMap);
		this.beanMap = BeanMap.create(this.obeject);
	}

	@SuppressWarnings("rawtypes")
	private Object generateBean(Map propertyMap) {
		BeanGenerator generator = new BeanGenerator();
		Set keySet = propertyMap.keySet();
		for (Iterator i = keySet.iterator(); i.hasNext();) {
			String key = (String) i.next();
			generator.addProperty(key, (Class) propertyMap.get(key));
		}
		return generator.create();
	}

	/**
	 * 给bean属性赋值
	 * 
	 * @param property
	 *            属性名
	 * @param value
	 *            值
	 */
	public void setValues(String property, Object value) {
		beanMap.put(property, value);
	}

	/**
	 * 通过属性名得到属性值
	 * 
	 * @param property
	 *            属性名
	 * @return 值
	 */
	public Object getValues(String property) {
		return beanMap.get(property);
	}

	/**
	 * 得到该实体bean对象
	 * 
	 * @return
	 */
	public Object getObject() {
		return this.obeject;
	}
}
