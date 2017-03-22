package track.container;

import track.container.config.Bean;
import track.container.config.Property;
import track.container.config.ValueType;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Основной класс контейнера
 * У него определено 2 публичных метода, можете дописывать свои методы и конструкторы
 */
public class Container {

    private List<Bean> beans;
    private Map<String, Object> objectsById;
    private Map<String, Object> objectsByClassName;

    // Реализуйте этот конструктор, используется в тестах!
    public Container(List<Bean> beans) {
        this.beans = beans;
        objectsById = new HashMap<>();
        objectsByClassName = new HashMap<>();
    }

    /**
     * Вернуть объект по имени бина из конфига
     * Например, Car car = (Car) container.getById("carBean")
     */
    public Object getById(String id) throws ReflectiveOperationException {
        Object obj = objectsById.get(id);
        if (obj == null) {
            Bean objBean = null;
            for (int i = 0; i < beans.size(); ++i) {
                if (beans.get(i).getId().equals(id)) {
                    objBean = beans.get(i);
                    break;
                }
            }
            obj = createInstance(objBean);
        }
        return obj;
    }

    private Object createInstance(Bean bean) throws ReflectiveOperationException {
        Class<?> clas = Class.forName(bean.getClassName());
        Object instance = clas.newInstance();
        for (Map.Entry<String, Property> entry : bean.getProperties().entrySet()) {
            Class<?> valueType;
            Object value;
            if (entry.getValue().getType().equals(ValueType.VAL)) {
                valueType = clas.getDeclaredField(entry.getValue().getName()).getType();
                value = parseValueTo(entry.getValue().getValue(), valueType);
            } else {
                value = getById(entry.getValue().getValue());
                valueType = value.getClass();
            }
            Method setter = clas.getMethod("set" + entry.getKey().substring(0, 1).toUpperCase() +
                    entry.getKey().substring(1), valueType);
            setter.invoke(instance, value);
        }
        objectsById.put(bean.getId(), instance);
        objectsById.put(bean.getClassName(), instance);
        return instance;
    }

    private Object parseValueTo(String value, Type valueType) {
        if (valueType == Integer.TYPE) {
            return Integer.parseInt(value);
        } else if (valueType == Double.TYPE) {
            return Double.parseDouble(value);
        } else if (valueType == Long.TYPE) {
            return Long.parseLong(value);
        } else if (valueType == Boolean.TYPE) {
            return Boolean.parseBoolean(value);
        }
        return value;
    }

    /**
     * Вернуть объект по имени класса
     * Например, Car car = (Car) container.getByClass("track.container.beans.Car")
     */
    public Object getByClass(String className) throws ReflectiveOperationException {
        Object obj = objectsByClassName.get(className);
        if (obj == null) {
            Bean objBean = null;
            for (int i = 0; i < beans.size(); ++i) {
                if (beans.get(i).getClassName().equals(className)) {
                    objBean = beans.get(i);
                    break;
                }
            }
            obj = createInstance(objBean);
        }
        return obj;
    }
}
