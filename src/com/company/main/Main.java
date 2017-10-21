package com.company.main;

import com.company.domain.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;


public class Main {
    //根据id查询用户信息，得到用户的一条记录
    @Test
    public void findUserByID() throws IOException {
        //Mybatis 配置文件
        String resource = "SqlMapConfig.xml";

        //得到配置文件流
        InputStream inputStream = Resources.getResourceAsStream(resource);

        //创建会话工厂,传入Mybatis的配置文件信息
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        //通过工厂得到SqlSession
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //通过SqlSession操作数据库
        //第一个参数：映射文件中Statement的id，等于 = namespace + "." + Statement的id
        //第二个参数：指定和映射文件中所匹配的parameterType类型的参数
        //sqlSession.selectOne 结果与映射文件中所匹配的resultType类型的对象
        User user = sqlSession.selectOne("test.findUserByID", 10);
        System.out.println(user.getUsername());
        //释放资源
        sqlSession.close();

    }

    @Test
    public void findAllUsers() throws IOException {
        InputStream is = Resources.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        List<User> lst = sqlSession.selectList("findAllUsers", "");
        for (User user : lst) {
            System.out.println(user.getUsername());
        }
        sqlSession.close();
    }

    @Test
    public void findUserByUsername() throws IOException {
        InputStream is = Resources.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        List<User> lst = sqlSession.selectList("findUserByUsername", "张三");
        for (User user : lst) {
            System.out.println(user.getUsername());
        }
        sqlSession.close();
    }
    @Test
    public void insetrUser() throws IOException, ParseException, ParseException {
        //Mybatis 配置文件
        String resource = "SqlMapConfig.xml";
        //得到配置文件流
        InputStream inputStream = Resources.getResourceAsStream(resource);
        //创建会话工厂,传入Mybatis的配置文件信息
        SqlSessionFactory  sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        //通过工厂得到SqlSession
        SqlSession sqlSession = sqlSessionFactory.openSession();
        User user = new User();
        SimpleDateFormat sdf = new SimpleDateFormat ("yyyy-MM-dd");
        user.setUsername("田志声");
        user.setSex("男");
        user.setBirthday(sdf.parse("2016-11-29"));
        user.setAddress("江西南昌");
        sqlSession.insert("test.insetrUser", user);
        System.out.println(user.getId());
        sqlSession.commit();
        //释放资源
        sqlSession.close();
    }
    public static void main(String[] args) {
        // write your code here

    }
}
