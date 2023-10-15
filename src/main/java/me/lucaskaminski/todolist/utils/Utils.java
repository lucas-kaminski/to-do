package me.lucaskaminski.todolist.utils;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapperImpl;

public class Utils {
  public static String[] getNullPropertiesNames(Object object) {
    final BeanWrapperImpl wrappedSource = new BeanWrapperImpl(object);
    PropertyDescriptor[] descriptors = wrappedSource.getPropertyDescriptors();
    Set<String> emptyNames = new HashSet<String>();
    for (PropertyDescriptor descriptor : descriptors) {
      Object srcValue = wrappedSource.getPropertyValue(descriptor.getName());
      if (srcValue == null)
        emptyNames.add(descriptor.getName());
    }
    String[] result = new String[emptyNames.size()];
    return emptyNames.toArray(result);
  }

  public static void copyNonNullProperties(Object src, Object target) {
    BeanUtils.copyProperties(src, target, getNullPropertiesNames(src));
  }
}
