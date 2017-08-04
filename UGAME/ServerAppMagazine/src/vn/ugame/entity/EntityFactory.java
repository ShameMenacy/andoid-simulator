package vn.ugame.entity;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import vn.ugame.exception.InvalidEntityClass;
import vn.ugame.entity.Column;
import vn.ugame.entity.Entity;

public class EntityFactory {

    public static EntityFactory createEntityFactory() {
        return new EntityFactory();
    }

    private EntityFactory() {
    }

    public <E> E createEntity(ResultSet rs, Class<E> entityClass) throws InvalidEntityClass {

        E object = null;
        //create new instance
        try {
            Entity entityAnotation = entityClass.getAnnotation(Entity.class);
            if (entityAnotation == null) {
                throw new Exception("entityClass parameter is not a entity Class");
            }
            object = entityClass.newInstance();
        } catch (Exception ex) {
            throw new InvalidEntityClass(ex.getMessage());
        }
        //Set value for all fields of return object
        List<Field> fieldList = new ArrayList<Field>();
        Field[] fields = entityClass.getDeclaredFields();        
        fieldList.addAll(Arrays.asList(fields));
        
        Class superClass = entityClass.getSuperclass();
        try {
            Entity supperEntityAnotation = (Entity) superClass.getAnnotation(Entity.class);
            if (supperEntityAnotation != null) {
                fieldList.addAll(Arrays.asList(superClass.getDeclaredFields()));
                fields = fieldList.toArray(new Field[0]);
            }
        } catch (Exception ex) {            
        }
        
        for (Field field : fields) {
            if (field.getType().isPrimitive()
                    || field.getType().getCanonicalName().equals("java.sql.Date")
                    || field.getType().getCanonicalName().equals("java.lang.String")) {
                try {
                    Column columnAnotation = field.getAnnotation(Column.class);
                    String columnName = columnAnotation.name();

                    String fileTypeName = field.getType().getSimpleName();
                    String getterName = "get" + fileTypeName.substring(0, 1).toUpperCase() + fileTypeName.substring(1);

                    Method getter = ResultSet.class.getMethod(getterName, String.class);
                    Object fieldValue = getter.invoke(rs, columnName);

                    String fieldName = field.getName();
                    String setterName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                    Method setter = entityClass.getMethod(setterName, field.getType());
                    setter.invoke(object, fieldValue);
                } catch (Exception ex) {
                    continue;
                }
            } else if (field.getType().getCanonicalName().equals("java.util.Date")) {
                try {
                    Column columnAnotation = field.getAnnotation(Column.class);
                    String columnName = columnAnotation.name();

                    java.sql.Timestamp timestamp = rs.getTimestamp(columnName);
                    java.util.Date fieldValue = new java.util.Date(timestamp.getTime());

                    String fieldName = field.getName();
                    String setterName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                    Method setter = entityClass.getMethod(setterName, field.getType());
                    setter.invoke(object, fieldValue);
                } catch (Exception ex) {
                    continue;
                }
            } else if (field.getType().getCanonicalName().equals("java.util.Locale")) {
                try {
                    Column columnAnotation = field.getAnnotation(Column.class);
                    String columnName = columnAnotation.name();

                    java.util.Locale fieldValue = new Locale(rs.getString(columnName));

                    String fieldName = field.getName();
                    String setterName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                    Method setter = entityClass.getMethod(setterName, field.getType());
                    setter.invoke(object, fieldValue);
                } catch (Exception ex) {
                    continue;
                }
            } else {
                try {
                    Object fieldValue = createEntity(rs, field.getType());

                    String fieldName = field.getName();
                    String setterName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                    Method setter = entityClass.getMethod(setterName, field.getType());
                    setter.invoke(object, fieldValue);
                } catch (Exception ex) {
                    continue;
                }
            }
        }
        return object;
    }
}
