package beans;

import java.beans.Beans;
import java.io.IOException;

public class BeanDSC extends Beans {
    public static void main(String[] args){
        BeanDSC beanDSC = new BeanDSC();
        Object o = BeanDSC.getInstanceOf(beanDSC, beanDSC.getClass());
        System.out.println(o);
        try {
            Object instantiate = BeanDSC.instantiate(null, beanDSC.getClass().getName());
            System.out.println(instantiate);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
