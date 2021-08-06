package com.lhw.mysql.generator;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lhw
 * @title
 * @description
 * @created 8/6/21 6:04 PM
 * @changeRecord
 */
public class RenamePlugin extends PluginAdapter {

    private static final Logger LOG = LoggerFactory.getLogger(RenamePlugin.class);
    private FullyQualifiedJavaType modelClass;

    public RenamePlugin() {
    }

    @Override
    public boolean validate(List<String> list) {
        return true;
    }

    @Override
    public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {

        sqlMapInsertSelectiveElementGenerated(document.getRootElement(), introspectedTable);

        sqlMapUpdateSelectiveElementGenerated(document.getRootElement(), introspectedTable);

        sqlMapBatchInsertElementGenerated(document.getRootElement(), introspectedTable);

        sqlMapBatchUpdateElementGenerated(document.getRootElement(), introspectedTable);

        sqlMapQueryByModelElementGenerated(document.getRootElement(), introspectedTable);

        sqlMapQueryByBizIdGenerated(document.getRootElement(), introspectedTable);

        sqlMapQueryByBizIdsElementGenerated(document.getRootElement(), introspectedTable);

        sqlMapQueryByPageElementGenerated(document.getRootElement(), introspectedTable);

        sqlMapALLColumnsCaluseGenerated(document.getRootElement(), introspectedTable);

        //sqlMapBaseColumnsCaluseGenerated(document.getRootElement(), introspectedTable);

        sqlMapWhereCaluseGenerated(document.getRootElement(), introspectedTable);

        return true;
    }


    @Override
    public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        interfaze.addImportedType(new FullyQualifiedJavaType("java.util.List"));
        interfaze.addImportedType(new FullyQualifiedJavaType("com.izk.spring.bean.MyBatisRepository"));
        interfaze.addImportedType(modelClass);
        interfaze.addAnnotation("@MyBatisRepository");
        //主键类型
        //String propertyType = introspectedTable.getPrimaryKeyColumns().get(0).getFullyQualifiedJavaType().getFullyQualifiedName();

        // insert selective
        Method insertSelective = new Method();
        insertSelective.setReturnType(new FullyQualifiedJavaType("int"));
        insertSelective.setName("insertSelective");
        insertSelective.addParameter(new Parameter(modelClass, "model"));
        interfaze.addMethod(insertSelective);

        // update selective
        Method update = new Method();
        update.setReturnType(new FullyQualifiedJavaType("int"));
        update.setName("updateSelective");
        update.addParameter(new Parameter(modelClass, "model"));
        interfaze.addMethod(update);

        //批量插入
        Method batchInsert = new Method();
        batchInsert.setReturnType(new FullyQualifiedJavaType("int"));
        batchInsert.setName("batchInsert");
        batchInsert.addParameter(new Parameter(new FullyQualifiedJavaType("java.util.List<" + modelClass + ">"), "list"));
        interfaze.addMethod(batchInsert);

        //批量更新
        Method batchUpdate = new Method();
        batchUpdate.setReturnType(new FullyQualifiedJavaType("int"));
        batchUpdate.setName("batchUpdate");
        batchUpdate.addParameter(new Parameter(new FullyQualifiedJavaType("java.util.List<" + modelClass + ">"), "list"));
        interfaze.addMethod(batchUpdate);

        //通过BizId查询
        Method queryByBizId = new Method();
        queryByBizId.setReturnType(modelClass);
        queryByBizId.setName("queryByBizId");
        queryByBizId.addParameter(new Parameter(new FullyQualifiedJavaType("java.lang.String"), "bizId"));
        interfaze.addMethod(queryByBizId);

        String propertyType = "String";
        //条件查询
        Method queryByModel = new Method();
        queryByModel.setReturnType(new FullyQualifiedJavaType("java.util.List<" + modelClass + ">"));
        queryByModel.setName("queryByModel");
        queryByModel.addParameter(new Parameter(modelClass, "model"));
        interfaze.addMethod(queryByModel);

        //分页查询
        interfaze.addImportedType(new FullyQualifiedJavaType("com.baomidou.mybatisplus.extension.plugins.pagination.Page"));
        Method queryByPage = new Method();
        queryByPage.setReturnType(new FullyQualifiedJavaType("java.util.List<" + modelClass + ">"));
        queryByPage.setName("queryByPage");
        queryByPage.addParameter(new Parameter(new FullyQualifiedJavaType("Page"), "page"));
        interfaze.addMethod(queryByPage);

        //按主键集合查询
        Method queryByBizIds = new Method();
        queryByBizIds.setReturnType(new FullyQualifiedJavaType("java.util.List<" + modelClass + ">"));
        queryByBizIds.setName("queryByBizIds");
        queryByBizIds.addParameter(new Parameter(new FullyQualifiedJavaType("java.util.List<" + propertyType + ">"), "bizIds"));
        interfaze.addMethod(queryByBizIds);

        return true;
    }

    /**
     * insert selective
     *
     * @param element
     * @param introspectedTable
     * @return
     */
    @Override
    public boolean sqlMapInsertSelectiveElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {

        XmlElement xmlElement = new XmlElement("insert");
        xmlElement.addAttribute(new Attribute("id", "insertSelective"));
        xmlElement.addAttribute(new Attribute("parameterType", modelClass.getFullyQualifiedName()));

        StringBuilder sql = new StringBuilder();
        sql.append("insert into ");
        sql.append(introspectedTable.getTableConfiguration().getTableName());

        XmlElement trimElement = new XmlElement("trim");

        xmlElement.addElement(new TextElement(sql.toString()));

        trimElement.addAttribute(new Attribute("prefix", "("));
        trimElement.addAttribute(new Attribute("suffix", ")"));
        trimElement.addAttribute(new Attribute("suffixOverrides", ","));

        List<IntrospectedColumn> introspectedColumns = introspectedTable.getAllColumns();
        for (IntrospectedColumn introspectedColumn : introspectedColumns) {
            String columnName = introspectedColumn.getActualColumnName();
            String javaPropertyName = introspectedColumn.getJavaProperty();
            XmlElement ifElement = new XmlElement("if");
            ifElement.addAttribute(new Attribute("test", javaPropertyName + " != null"));
            ifElement.addElement(new TextElement(columnName + ","));
            trimElement.addElement(ifElement);
        }
        xmlElement.addElement(trimElement);

        // ************************************************************************

        XmlElement trimElement2 = new XmlElement("trim");
        trimElement2.addAttribute(new Attribute("prefix", "values ("));
        trimElement2.addAttribute(new Attribute("suffix", ")"));
        trimElement2.addAttribute(new Attribute("suffixOverrides", ","));

        xmlElement.addElement(trimElement2);
        List<IntrospectedColumn> introspectedColumns2 = introspectedTable.getAllColumns();
        for (IntrospectedColumn introspectedColumn : introspectedColumns2) {
            String javaPropertyName = introspectedColumn.getJavaProperty();
            String jdbcPropertyName = introspectedColumn.getJdbcTypeName();
            XmlElement ifElement = new XmlElement("if");
            ifElement.addAttribute(new Attribute("test", javaPropertyName + " != null"));

            ifElement.addElement(new TextElement("#{" + javaPropertyName + ", jdbcType=" + jdbcPropertyName + "}, "));
            trimElement2.addElement(ifElement);
        }

        element.addElement(xmlElement);


        return true;
    }

    /**
     * update selective
     *
     * @param element
     * @param introspectedTable
     * @return
     */
    public boolean sqlMapUpdateSelectiveElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {

        XmlElement xmlElement = new XmlElement("update");
        xmlElement.addAttribute(new Attribute("id", "updateSelective"));
        xmlElement.addAttribute(new Attribute("parameterType", modelClass.getFullyQualifiedName()));

        StringBuilder sql = new StringBuilder();
        sql.append("update ");
        sql.append(introspectedTable.getTableConfiguration().getTableName());

        XmlElement setElement = new XmlElement("set");
        xmlElement.addElement(new TextElement(sql.toString()));

        List<IntrospectedColumn> introspectedColumns = introspectedTable.getAllColumns();
        for (IntrospectedColumn introspectedColumn : introspectedColumns) {
            String columnName = introspectedColumn.getActualColumnName();
            String javaPropertyName = introspectedColumn.getJavaProperty();
            String jdbcPropertyName = introspectedColumn.getJdbcTypeName();

            XmlElement ifElement = new XmlElement("if");
            ifElement.addAttribute(new Attribute("test", javaPropertyName + " != null"));
            ifElement.addElement(new TextElement(columnName + " = #{" + javaPropertyName + ", jdbcType= " + jdbcPropertyName + "}, "));
            setElement.addElement(ifElement);
        }
        xmlElement.addElement(setElement);
        xmlElement.addElement(new TextElement("where biz_id = #{bizId,jdbcType=VARCHAR}"));
        element.addElement(xmlElement);

        return true;

    }

    /**
     * 批量添加
     *
     * @param element
     * @param introspectedTable
     * @return
     */
    public boolean sqlMapBatchInsertElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        XmlElement xmlElement = new XmlElement("insert");
        xmlElement.addAttribute(new Attribute("id", "batchInsert"));
        xmlElement.addAttribute(new Attribute("keyColumn", "id"));
        xmlElement.addAttribute(new Attribute("keyProperty", "id"));
        xmlElement.addAttribute(new Attribute("useGeneratedKeys", "true"));
        StringBuilder sql = new StringBuilder();
        StringBuilder valueSQL = new StringBuilder();
        sql.append("insert into " + introspectedTable.getTableConfiguration().getTableName() + " (");
        List<IntrospectedColumn> baseColumns = introspectedTable.getBaseColumns();
        int n = 0;
        valueSQL.append("\t(");
        for (IntrospectedColumn baseColumn : baseColumns) {
            sql.append(baseColumn.getActualColumnName() + ", ");
            valueSQL.append("#{item." + baseColumn.getJavaProperty() + ",jdbcType=" + baseColumn.getJdbcTypeName() + "},");
            if (n == 3) {
                sql.append("\n\t\t");
                valueSQL.append("\n\t\t");
                n = 0;
            }
            n++;
        }
        sql.deleteCharAt(sql.lastIndexOf(","));
        valueSQL.deleteCharAt(valueSQL.lastIndexOf(","));
        sql.append(")");
        valueSQL.append(")");
        sql.append("\n\tvalues");
        xmlElement.addElement(new TextElement(sql.toString()));
        XmlElement foreachElement = new XmlElement("foreach");
        foreachElement.addAttribute(new Attribute("collection", "list"));
        foreachElement.addAttribute(new Attribute("item", "item"));
        foreachElement.addAttribute(new Attribute("index", "index"));
        foreachElement.addAttribute(new Attribute("separator", ","));
        foreachElement.addElement(new TextElement(valueSQL.toString()));
        xmlElement.addElement(foreachElement);
        element.addElement(xmlElement);
        return true;
    }

    /**
     * 批量更新
     *
     * @param element
     * @param introspectedTable
     * @return
     */
    public boolean sqlMapBatchUpdateElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {

        XmlElement xmlElement = new XmlElement("update");
        xmlElement.addAttribute(new Attribute("id", "batchUpdate"));
        xmlElement.addAttribute(new Attribute("parameterType", "java.util.List"));
        StringBuilder sql = new StringBuilder();
        List<IntrospectedColumn> baseColumns = introspectedTable.getBaseColumns();
        int n = 0;
        sql.append("update " + introspectedTable.getTableConfiguration().getTableName());
        sql.append("\n\t  <set>\n\t\t");
        for (IntrospectedColumn baseColumn : baseColumns) {
            sql.append(baseColumn.getActualColumnName() + " = #{item." + baseColumn.getJavaProperty() + ",jdbcType=" + baseColumn.getJdbcTypeName() + "},");
            if (n == 3) {
                sql.append("\n\t\t");
                n = 0;
            }
            n++;
        }
        sql.deleteCharAt(sql.lastIndexOf(","));
        sql.append("\n\t  </set>");
        XmlElement foreachElement = new XmlElement("foreach");
        foreachElement.addAttribute(new Attribute("collection", "list"));
        foreachElement.addAttribute(new Attribute("item", "item"));
        foreachElement.addAttribute(new Attribute("index", "index"));
        foreachElement.addAttribute(new Attribute("open", ""));
        foreachElement.addAttribute(new Attribute("close", ""));
        foreachElement.addAttribute(new Attribute("separator", ";"));
        foreachElement.addElement(new TextElement(sql.toString()));
        String columnName = introspectedTable.getPrimaryKeyColumns().get(0).getActualColumnName();
        String javaProperty = introspectedTable.getPrimaryKeyColumns().get(0).getJavaProperty();
        foreachElement.addElement(new TextElement("where " + columnName + " = #{item." + javaProperty + "}"));
        xmlElement.addElement(foreachElement);
        element.addElement(xmlElement);
        return true;

    }


    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        modelClass = topLevelClass.getType();
        topLevelClass.addImportedType(modelClass);
        topLevelClass.addImportedType(new FullyQualifiedJavaType("lombok.Data"));
        topLevelClass.addAnnotation("@Data");
        addClassComment(topLevelClass, introspectedTable);
        return true;
    }


    //@Override
    public void initialized1(IntrospectedTable introspectedTable) {

        List<String> requiredColumns = new ArrayList<>();
        requiredColumns.add("id");
        requiredColumns.add("biz_id");
        requiredColumns.add("city_code");
        requiredColumns.add("deleted");
        requiredColumns.add("version");
        requiredColumns.add("create_time");
        requiredColumns.add("modify_time");
        requiredColumns.add("creator_id");
        requiredColumns.add("creator_name");
        requiredColumns.add("operator_id");
        requiredColumns.add("operator_name");
        String str = requiredColumns.toString();
        for (IntrospectedColumn baseColumn : introspectedTable.getBaseColumns()) {
            if (requiredColumns.contains(baseColumn.getActualColumnName())) {
                requiredColumns.remove(baseColumn.getActualColumnName());
            }
        }
        for (IntrospectedColumn baseColumn : introspectedTable.getPrimaryKeyColumns()) {
            if (requiredColumns.contains(baseColumn.getActualColumnName())) {
                requiredColumns.remove(baseColumn.getActualColumnName());
            }
        }
        StringBuilder StringBuilder = new StringBuilder();
        //检测表是否包含必选字段
        if (requiredColumns.size() > 0) {
            StringBuilder.append("\n");
            StringBuilder.append("\n");
            StringBuilder.append("#############################################################################################################");
            StringBuilder.append("\n");
            StringBuilder.append("#############################################################################################################");
            StringBuilder.append("\n");
            StringBuilder.append("\n");
            StringBuilder.append("\t\t\t\t\t\t表" + introspectedTable.getTableConfiguration().getTableName() + "必须包含如下字段");
            StringBuilder.append("\n");
            StringBuilder.append("\t\t" + str);
            StringBuilder.append("\n");
            StringBuilder.append("\n");
            StringBuilder.append("#############################################################################################################");
            StringBuilder.append("\n");
            StringBuilder.append("#############################################################################################################");
            LOG.error("\033[31;0m" + StringBuilder.toString() + "\033[0m");
            StringBuilder.append("\n");
            System.exit(-1);
        }
        String javaFile = introspectedTable.getMyBatis3JavaMapperType();
        javaFile = javaFile.replace("Mapper", "Dao");
        int index = javaFile.lastIndexOf(".");
        javaFile = javaFile.substring(0, index + 1) + "I" + javaFile.substring(index + 1);
        introspectedTable.setMyBatis3JavaMapperType(javaFile);
        String mapperFileName = introspectedTable.getMyBatis3XmlMapperFileName();
        introspectedTable.setMyBatis3XmlMapperFileName(mapperFileName);
    }


    @Override
    public boolean sqlMapUpdateByPrimaryKeyWithoutBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        element.getAttributes().remove(0);
        element.getAttributes().add(0, new Attribute("id", "update"));
        return true;
    }

    public boolean sqlMapQueryByModelElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        XmlElement xmlElement = new XmlElement("select");
        xmlElement.addAttribute(new Attribute("id", "queryByModel"));
        xmlElement.addAttribute(new Attribute("parameterType", modelClass.getFullyQualifiedName()));
        xmlElement.addAttribute(new Attribute("resultMap", "BaseResultMap"));
        xmlElement.addElement(new TextElement("select <include refid=\"AllColumns\"/>"));
        xmlElement.addElement(new TextElement("from " + introspectedTable.getTableConfiguration().getTableName()));
        xmlElement.addElement(new TextElement("<if test=\"_parameter != null\">"));
        xmlElement.addElement(new TextElement("\t<include refid=\"WhereClause\"/>"));
        xmlElement.addElement(new TextElement("</if>"));
        element.addElement(xmlElement);
        return true;
    }

    /**
     * 分页查询
     *
     * @param element
     * @param introspectedTable
     * @return
     */
    public boolean sqlMapQueryByPageElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        XmlElement xmlElement = new XmlElement("select");
        xmlElement.addAttribute(new Attribute("id", "queryByPage"));
        xmlElement.addAttribute(new Attribute("parameterType", "com.baomidou.mybatisplus.extension.plugins.pagination.Page"));
        xmlElement.addAttribute(new Attribute("resultMap", "BaseResultMap"));
        xmlElement.addElement(new TextElement("select <include refid=\"AllColumns\"/> from " + introspectedTable.getTableConfiguration().getTableName()));
        xmlElement.addElement(new TextElement("<if test=\"_parameter != null\">"));
        xmlElement.addElement(new TextElement("\t<include refid=\"WhereClause\"/>"));
        xmlElement.addElement(new TextElement("</if>"));
        element.addElement(xmlElement);
        return true;

    }

    /**
     * where条件
     *
     * @param element
     * @param introspectedTable
     * @return
     */
    public boolean sqlMapWhereCaluseGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        List<IntrospectedColumn> introspectedColumns = introspectedTable.getAllColumns();
        XmlElement whereClause = new XmlElement("sql");
        whereClause.addAttribute(new Attribute("id", "WhereClause"));
        XmlElement where = new XmlElement("where");
        for (IntrospectedColumn introspectedColumn : introspectedColumns) {
            String columnName = introspectedColumn.getActualColumnName();
            String javaPropertyName = introspectedColumn.getJavaProperty();
            XmlElement ifElement = new XmlElement("if");
            ifElement.addAttribute(new Attribute("test", javaPropertyName + " != null"));
            ifElement.addElement(new TextElement(" and " + columnName + " = #{" + javaPropertyName + "}"));
            where.addElement(ifElement);
        }
        whereClause.addElement(where);
        element.addElement(whereClause);
        return true;
    }


    /**
     * 所有列
     *
     * @param element
     * @param introspectedTable
     * @return
     */
    public boolean sqlMapALLColumnsCaluseGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        List<IntrospectedColumn> list = introspectedTable.getAllColumns();
        StringBuilder sql = new StringBuilder();
        sql.append("<sql id=\"AllColumns\">");
        for (int i = 0; i < list.size(); i++) {
            sql.append(" " + list.get(i).getActualColumnName() + ",");
        }
        sql.deleteCharAt(sql.lastIndexOf(","));
        sql.append("</sql>");
        element.addElement(new TextElement(sql.toString()));
        return true;
    }

    /**
     * 基本信息列
     *
     * @param element
     * @param introspectedTable
     * @return
     */
    public boolean sqlMapBaseColumnsCaluseGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        List<IntrospectedColumn> list = introspectedTable.getBaseColumns();
        StringBuilder sql = new StringBuilder();
        sql.append("<sql id=\"BaseColumns\">");
        List<String> excludeColumns = new ArrayList<String>();
        excludeColumns.add("id");
        excludeColumns.add("create_time");
        excludeColumns.add("modify_time");
        excludeColumns.add("creator_id");
        excludeColumns.add("creator_name");
        excludeColumns.add("operator_id");
        excludeColumns.add("operator_name");
        for (int i = 0; i < list.size(); i++) {
            if (!excludeColumns.contains(list.get(i).getActualColumnName())) {
                sql.append(" " + list.get(i).getActualColumnName() + ",");
            }

        }
        sql.deleteCharAt(sql.lastIndexOf(","));
        sql.append("</sql>");
        element.addElement(new TextElement(sql.toString()));
        return true;
    }


    /**
     * 根据主键集合查询
     *
     * @param element
     * @param introspectedTable
     * @return
     */
    public boolean sqlMapQueryByBizIdsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        XmlElement xmlElement = new XmlElement("select");
        xmlElement.addAttribute(new Attribute("id", "queryByBizIds"));
        xmlElement.addAttribute(new Attribute("parameterType", "java.util.List"));
        xmlElement.addAttribute(new Attribute("resultMap", "BaseResultMap"));
        StringBuilder sql = new StringBuilder();
        sql.append("select <include refid=\"AllColumns\"/>");
        sql.append(" from " + introspectedTable.getTableConfiguration().getTableName());
        sql.append(" where biz_id in ");
        xmlElement.addElement(new TextElement(sql.toString()));
        XmlElement foreachElement = new XmlElement("foreach");
        foreachElement.addAttribute(new Attribute("collection", "list"));
        foreachElement.addAttribute(new Attribute("item", "item"));
        foreachElement.addAttribute(new Attribute("index", "index"));
        foreachElement.addAttribute(new Attribute("open", "("));
        foreachElement.addAttribute(new Attribute("close", ")"));
        foreachElement.addAttribute(new Attribute("separator", ","));
        foreachElement.addElement(new TextElement("#{item}"));
        xmlElement.addElement(foreachElement);
        element.addElement(xmlElement);
        return true;
    }


    public boolean sqlMapQueryByBizIdGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        XmlElement xmlElement = new XmlElement("select");
        xmlElement.addAttribute(new Attribute("id", "queryByBizId"));
        xmlElement.addAttribute(new Attribute("parameterType", "java.lang.String"));
        xmlElement.addAttribute(new Attribute("resultMap", "BaseResultMap"));
        xmlElement.addElement(new TextElement("select <include refid=\"AllColumns\"/>"));
        xmlElement.addElement(new TextElement("from " + introspectedTable.getTableConfiguration().getTableName()));
        xmlElement.addElement(new TextElement("where biz_id = #{bizId,jdbcType=VARCHAR}"));
        element.addElement(xmlElement);
        return true;
    }

    @Override
    public boolean modelSetterMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        return false;
    }

    @Override
    public boolean modelGetterMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        return false;
    }

    @Override
    public boolean modelFieldGenerated(Field field, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        if (field.getName().equals("deleted")) {
            field.setType(PrimitiveTypeWrapper.getBooleanInstance());
        } else if ("TINYINT".equals(introspectedColumn.getJdbcTypeName()) || "SMALLINT".equals(introspectedColumn.getJdbcTypeName())) {
            field.setType(PrimitiveTypeWrapper.getIntegerInstance());
        } else if ("TIMESTAMP".equals(introspectedColumn.getJdbcTypeName())) {
            field.setType(new FullyQualifiedJavaType("java.time.LocalDateTime"));
        } else if ("DATETIME".equals(introspectedColumn.getJdbcTypeName())) {
            field.setType(new FullyQualifiedJavaType("java.time.LocalDateTime"));
        } else if ("DATE".equals(introspectedColumn.getJdbcTypeName())) {
            field.setType(new FullyQualifiedJavaType("java.time.LocalDate"));
        } else if ("TIME".equals(introspectedColumn.getJdbcTypeName())) {
            field.setType(new FullyQualifiedJavaType("java.time.LocalTime"));
        }
        addFieldComment(field, introspectedColumn);
        return true;
    }

    @Override
    public boolean sqlMapResultMapWithoutBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        List<Element> list = element.getElements();
        for (Element item : list) {
            XmlElement xmlElement = (XmlElement) item;
            List<Attribute> attributes = xmlElement.getAttributes();
            for (Attribute attribute : attributes) {
                if (attribute.getName().equals("jdbcType") && attribute.getValue().equals("DECIMAL")) {
                    break;
                }
            }
        }
        return true;
    }

    /**
     * 添加类注释
     *
     * @param topLevelClass
     * @param introspectedTable
     */
    private void addClassComment(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        topLevelClass.addJavaDocLine("/**");
        StringBuilder sb = new StringBuilder();
        sb.append(" * ");
        sb.append(introspectedTable.getFullyQualifiedTable());
        sb.append(' ');
        sb.append(introspectedTable.getRemarks());
        topLevelClass.addJavaDocLine(sb.toString());
        topLevelClass.addJavaDocLine(" */");
    }

    /**
     * // 添加字段注释
     *
     * @param field
     * @param introspectedColumn
     */
    private void addFieldComment(Field field, IntrospectedColumn introspectedColumn) {
        StringBuilder sb = new StringBuilder();
        field.addJavaDocLine("/**");
        sb.append(" * ");
        sb.append(introspectedColumn.getActualColumnName());
        if (introspectedColumn.getRemarks() != null) {
            sb.append(" " + introspectedColumn.getRemarks());
        }
        field.addJavaDocLine(sb.toString());
        field.addJavaDocLine(" */");
    }

    @Override
    public boolean clientSelectByPrimaryKeyMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientDeleteByPrimaryKeyMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean clientSelectAllMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean sqlMapDeleteByPrimaryKeyElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return false;
    }


    @Override
    public boolean sqlMapSelectByPrimaryKeyElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return false;
    }

    @Override
    public boolean sqlMapSelectAllElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return false;
    }

}
