//import freemarker.template.TemplateExceptionHandler;

import com.google.common.base.CaseFormat;
import freemarker.template.TemplateExceptionHandler;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.*;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.example.janche.common.model.ProjectConstant.MAPPER_INTERFACE_REFERENCE;

/**
 * <P></P>
 * 代码生成器，根据数据表名称生成对应的Model、Mapper、Service、Controller简化开发
 *
 * @author lirong
 * @version v1.0
 * @since 2018/1/25 11:45
 */
public class CodeGenerator {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/boot2-oauth?useUnicode=true&characterEncoding=utf8&useSSL=false";
    private static final String JDBC_USERNAME = "root";
    private static final String JDBC_PASSWORD = "root";
    private static final String JDBC_DIVER_CLASS_NAME = "com.mysql.jdbc.Driver";

    /**
     * 项目在硬盘上的基础路径
     */
    private static final String PROJECT_PATH = System.getProperty("user.dir");
    /**
     * 模板位置
     */
    private static final String TEMPLATE_FILE_PATH = PROJECT_PATH + "/backend/src/test/resources/generator/template";

    private static final String JAVA_PATH = "/backend/src/main/java"; //java文件路径
    private static final String RESOURCES_PATH = "/backend/src/main/resources";//资源文件路径

    /**
     * 项目基础包名称
     */
    private static final String BASE_PACKAGE_PATH = "/com/example/janche";
    private static final String BASE_PACKAGE_ABSOLUTE_PATH = PROJECT_PATH + JAVA_PATH + "/com/example/janche";
    // controller 的路径 不一样
    private static final String CONTROLLER_PATH = PROJECT_PATH + "/web/src/main/java/com/example/janche";

    public static final String BASE_PACKAGE = "com.example.janche";

    // private static final String MODULE_PACKAGE = BASE_PACKAGE + ".modules";
    private static final String MODULE_PACKAGE = BASE_PACKAGE;

    private static final String DOMAIN_PACKAGE_NAME = ".domain";
    private static final String DAO_PACKAGE_NAME = ".dao";
    private static final String MAPPER_PACKAGE_NAME = ".mapper";

    private static final String AUTHOR = "codeGenerator"; // TODO 此处可替换作者
    private static final String DATE = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());//@date
    /**
     * 默认的模板文件夹
     */
    private static final String DEFAULT_TEMPLATE_FOLDER = "default";

    public static void main(String[] args) {
        // genCodeByCustomModelName("menu_right", "UC", "MenuRight");  // 调用默认的模板
//        genCodeByCustomModelNameAndTemplate("user", "user", "User","simpleDict"); // 调用simpleDict的模板
        genCodeByCustomModelNameAndTemplate("test", "test2", "Test","simpleDict"); // 调用simpleDict的模板


    }

    /**
     * 通过数据表名称生成代码，Model 名称通过解析数据表名称获得，下划线转大驼峰的形式。
     * 如输入表名称 "t_user_detail" 将生成 TUserDetail、TUserDetailMapper、TUserDetailService ...
     */
    public static void genCode(String tableName, String moduleName) {
        genCodeByCustomModelNameAndTemplate(tableName, moduleName, null, DEFAULT_TEMPLATE_FOLDER);
    }

    /**
     * 通过数据表名称，和自定义的 Model 名称生成代码
     * 如输入表名称 "t_user_detail" 和自定义的 Model 名称 "User" 将生成 User、UserMapper、UserService ...
     *
     * @param tableName 数据表名称
     * @param modelName 自定义的 Model 名称
     */
    public static void genCodeByCustomModelName(String tableName,
                                                String moduleName,
                                                String modelName) {
        genCodeByCustomModelNameAndTemplate(tableName, moduleName, modelName, DEFAULT_TEMPLATE_FOLDER);
    }

    /**
     * 通过模板生成代码
     *
     * @param tableName
     */
    public static void genCodeByTemplate(String tableName,
                                         String moduleName,
                                         String templateFolder) {
        genCodeByCustomModelNameAndTemplate(tableName, moduleName, null, templateFolder);
    }

    public static void genCodeByCustomModelNameAndTemplate(String tableName,
                                                           String moduleName,
                                                           String modelName,
                                                           String templateFolder) {
        if (StringUtils.isEmpty(moduleName)) {
            throw new RuntimeException("模块名称 不能为空");
        }
        genModelAndMapper(tableName, moduleName, modelName);
        genService(tableName, moduleName, modelName, templateFolder);
        genController(tableName, moduleName, modelName, templateFolder);
    }

    public static void genModelAndMapper(String tableName,
                                         String moduleName,
                                         String modelName) {
        //env
        Context context = new Context(ModelType.FLAT);
        context.setId("Potato");
        context.setTargetRuntime("MyBatis3Simple");
        context.addProperty(PropertyRegistry.CONTEXT_BEGINNING_DELIMITER, "`");
        context.addProperty(PropertyRegistry.CONTEXT_ENDING_DELIMITER, "`");

        //JDBC
        JDBCConnectionConfiguration jdbcConnectionConfiguration = new JDBCConnectionConfiguration();
        jdbcConnectionConfiguration.setConnectionURL(JDBC_URL);
        jdbcConnectionConfiguration.setUserId(JDBC_USERNAME);
        jdbcConnectionConfiguration.setPassword(JDBC_PASSWORD);
        jdbcConnectionConfiguration.setDriverClass(JDBC_DIVER_CLASS_NAME);
        context.setJdbcConnectionConfiguration(jdbcConnectionConfiguration);

        //Plugin
        PluginConfiguration pluginConfiguration = new PluginConfiguration();
        pluginConfiguration.setConfigurationType("tk.mybatis.mapper.generator.MapperPlugin");
        pluginConfiguration.addProperty("mappers", MAPPER_INTERFACE_REFERENCE);
        context.addPluginConfiguration(pluginConfiguration);

        //Domain
        JavaModelGeneratorConfiguration javaModelGeneratorConfiguration = new JavaModelGeneratorConfiguration();
        javaModelGeneratorConfiguration.setTargetProject(PROJECT_PATH + JAVA_PATH);
        javaModelGeneratorConfiguration.setTargetPackage(MODULE_PACKAGE + "." + moduleName + DOMAIN_PACKAGE_NAME);
        context.setJavaModelGeneratorConfiguration(javaModelGeneratorConfiguration);

        //DAO
        JavaClientGeneratorConfiguration javaClientGeneratorConfiguration = new JavaClientGeneratorConfiguration();
        javaClientGeneratorConfiguration.setTargetProject(PROJECT_PATH + JAVA_PATH);
        javaClientGeneratorConfiguration.setTargetPackage(MODULE_PACKAGE + "." + moduleName + DAO_PACKAGE_NAME);
        javaClientGeneratorConfiguration.setConfigurationType("XMLMAPPER");
        context.setJavaClientGeneratorConfiguration(javaClientGeneratorConfiguration);

        //XML
        SqlMapGeneratorConfiguration sqlMapGeneratorConfiguration = new SqlMapGeneratorConfiguration();
        sqlMapGeneratorConfiguration.setTargetProject(PROJECT_PATH + RESOURCES_PATH);
        sqlMapGeneratorConfiguration.setTargetPackage("mapper." + moduleName);
        context.setSqlMapGeneratorConfiguration(sqlMapGeneratorConfiguration);

        TableConfiguration tableConfiguration = new TableConfiguration(context);
        tableConfiguration.setTableName(tableName);
        if (StringUtils.isNotEmpty(modelName)) tableConfiguration.setDomainObjectName(modelName);
        tableConfiguration.setGeneratedKey(new GeneratedKey("id", "Mysql", true, null));
        context.addTableConfiguration(tableConfiguration);

        List<String> warnings;
        MyBatisGenerator generator;
        try {
            Configuration config = new Configuration();
            config.addContext(context);
            config.validate();

            boolean overwrite = true;
            DefaultShellCallback callback = new DefaultShellCallback(overwrite);
            warnings = new ArrayList<String>();
            generator = new MyBatisGenerator(config, callback, warnings);
            generator.generate(null);
        } catch (Exception e) {
            throw new RuntimeException("生成Model和Mapper失败", e);
        }

        if (generator.getGeneratedJavaFiles().isEmpty() || generator.getGeneratedXmlFiles().isEmpty()) {
            throw new RuntimeException("生成Model和Mapper失败：" + warnings);
        }
        if (StringUtils.isEmpty(modelName)) modelName = tableNameConvertUpperCamel(tableName);
        System.out.println(modelName + ".java 生成成功");
        System.out.println(modelName + "Mapper.java 生成成功");
        System.out.println(modelName + "Mapper.xml 生成成功");
    }

    public static void genService(String tableName, String moduleName, String modelName, String templateFolder) {
        try {
            freemarker.template.Configuration cfg = getConfiguration(templateFolder);

            Map<String, Object> data = new HashMap<>();
            data.put("date", DATE);
            data.put("author", AUTHOR);
            String modelNameUpperCamel = StringUtils.isEmpty(modelName) ? tableNameConvertUpperCamel(tableName) : modelName;
            data.put("modelNameUpperCamel", modelNameUpperCamel);
            data.put("modelNameLowerCamel", tableNameConvertLowerCamel(tableName));
            data.put("moduleName", moduleName);
            data.put("basePackage", BASE_PACKAGE);
            data.put("modulePackage", MODULE_PACKAGE);

            File file = new File(BASE_PACKAGE_ABSOLUTE_PATH + "/" + moduleName + "/service/" + modelNameUpperCamel + "Service.java");
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            cfg.getTemplate("service.ftl").process(data,
                    new FileWriter(file));
            System.out.println(modelNameUpperCamel + "Service.java 生成成功");

            File file1 = new File(BASE_PACKAGE_ABSOLUTE_PATH + "/" + moduleName + "/service/impl/" + modelNameUpperCamel + "ServiceImpl.java");
            if (!file1.getParentFile().exists()) {
                file1.getParentFile().mkdirs();
            }
            cfg.getTemplate("service-impl.ftl").process(data,
                    new FileWriter(file1));
            System.out.println(modelNameUpperCamel + "ServiceImpl.java 生成成功");
        } catch (Exception e) {
            throw new RuntimeException("生成Service失败", e);
        }
    }

    public static void genController(String tableName, String moduleName, String modelName, String templateFolder) {
        try {
            freemarker.template.Configuration cfg = getConfiguration(templateFolder);

            Map<String, Object> data = new HashMap<>();
            data.put("date", DATE);
            data.put("author", AUTHOR);
            String modelNameUpperCamel = StringUtils.isEmpty(modelName) ? tableNameConvertUpperCamel(tableName) : modelName;
            data.put("baseRequestMapping", modelNameConvertMappingPath(modelNameUpperCamel));
            data.put("modelNameUpperCamel", modelNameUpperCamel);
            data.put("modelNameLowerCamel", CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, modelNameUpperCamel));
            data.put("moduleName", moduleName);
            data.put("basePackage", BASE_PACKAGE);
            data.put("modulePackage", MODULE_PACKAGE);

            // File file = new File(BASE_PACKAGE_ABSOLUTE_PATH + "/modules/" + moduleName + "/controller/" + modelNameUpperCamel + "Controller.java");
            File file = new File(CONTROLLER_PATH + "/web/controller/" + moduleName + "/" + modelNameUpperCamel + "Controller.java");
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            //cfg.getTemplate("controller-restful.ftl").process(data, new FileWriter(file));
            cfg.getTemplate("controller.ftl").process(data, new FileWriter(file));
            System.out.println(modelNameUpperCamel + "Controller.java 生成成功");
        } catch (Exception e) {
            throw new RuntimeException("生成Controller失败", e);
        }
    }

    private static freemarker.template.Configuration getConfiguration(String templateFileFolder) throws IOException {
        freemarker.template.Configuration cfg = new freemarker.template.Configuration(freemarker.template
                .Configuration
                .VERSION_2_3_23);
        File file = new File(TEMPLATE_FILE_PATH + "/" + templateFileFolder);
        cfg.setDirectoryForTemplateLoading(file);
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
        return cfg;
    }

    private static String tableNameConvertLowerCamel(String tableName) {
        return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, tableName.toLowerCase());
    }

    private static String tableNameConvertUpperCamel(String tableName) {
        return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, tableName.toLowerCase());

    }

    private static String tableNameConvertMappingPath(String tableName) {
        tableName = tableName.toLowerCase();//兼容使用大写的表名
        return "/" + (tableName.contains("_") ? tableName.replaceAll("_", "/") : tableName);
    }

    private static String modelNameConvertMappingPath(String modelName) {
        String tableName = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, modelName);
        return tableNameConvertMappingPath(tableName);
    }

    private static String packageConvertPath(String packageName) {
        return String.format("/%s/", packageName.contains(".") ? packageName.replaceAll("\\.", "/") : packageName);
    }

}
