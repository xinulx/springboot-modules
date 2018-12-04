package beans;

import java.beans.BeanDescriptor;

public class BeanDescriptorDSC extends BeanDescriptor {

    private String name = "21";

    public BeanDescriptorDSC(Class<?> beanClass) {
        super(beanClass);
    }

    public static void main(String[] args){
        BeanDescriptorDSC dsc = new BeanDescriptorDSC(BeanDescriptorDSC.class);
        System.out.println(dsc.name);
        System.out.println(dsc.getBeanClass());
        System.out.println(dsc.attributeNames());
        System.out.println(dsc.getName());
        System.out.println(dsc.getShortDescription());
        System.out.println(dsc.getDisplayName());
        System.out.println(dsc.getCustomizerClass());
    }
}
