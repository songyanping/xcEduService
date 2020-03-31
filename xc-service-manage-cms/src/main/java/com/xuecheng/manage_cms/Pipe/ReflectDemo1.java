package com.xuecheng.manage_cms.Pipe;



public class ReflectDemo1 {

    //1.Class.forname("全类名")

    public static void main(String[] ages) throws ClassNotFoundException {
      Class cls1= Class.forName("com.xuecheng.manage_cms.Pipe.Person");
      System.out.println(cls1);

      Class cls2 = Person.class;
      System.out.println(cls2);

      Person p = new Person();
      Class cls3 = p.getClass();
      System.out.println(cls3);

    }
}
