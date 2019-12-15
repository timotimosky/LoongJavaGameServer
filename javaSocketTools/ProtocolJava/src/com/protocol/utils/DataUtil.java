package com.protocol.utils;

/**
 * 定义所有的公共数据 
 * @author  Cico.姜彪 <cico216@163.com>
 * @date 	下午2:28:55
 * @version 1.0
 */
public abstract class DataUtil
{
	private DataUtil(){}
	
	public static final String ENTER = "\r\n";
	
	public static final String TAB = "    ";
	
	public static final String COMMA = ";";
	
	public interface XMLKey {
		String MODULE = "module";
		
		String MODEL_NAME  = "module_name";

		String MODELS = "models";
		
		String COMMAND = "command";
		
		String PARAM = "param";
		
		String NAME = "name";
		
		String PROXY = "proxy";
		
		String VALUE = "value";
		
		String INFO = "info";
		
		String PACKAGE = "package";
		
		String CMD_NAME = "cmd_name";
		
		String DIRECTION = "direction";
		
		String TYPE = "type";
				
		String SIZE = "size";
		
		String REMARK = "remark";
	}
	
	public interface TMPKey {
		String COMMANDVALUE = "#{commandValue}";
		
		String COMMAND = "#{command}";
		
		String COMMANDDETAIL = "#{commandDetail}";
		
		String DECLARE = "#declare=";
		
		String COMMANDMODEL = "#CommandModel=";
		
		String PARAMS_EQUAL = "#params=";
		
		String COMMAND_ARRAY_EQUAL = "#commandArray=";
		
		String COMMAND_LIST_EQUAL = "#commandList=";
		
		String SPLIT = "<-->";
		
		String CLASS_NAME = "#{className}";
		
		String CLASS_NAME_1 = "#{className1}";
		
		String CLASS_NAME_2 = "#{className2}";
		
		String CN_NAME = "#{cnName}";
		
		String EN_NAME = "#{enName}";

		String COMMAND_ARRAY = "#{commandArray}";
		
		String TYPE = "#{type}";
		
		String BASE_TYPE = "#{baseType}";
		
		String PARAMS = "#{params}";
		
		String NAME = "#{name}";
		
		String SIZE = "#{size}";
		
		String INFO = "#{info}";
		
		String LIST = "#{list}";
		
		String VALUE = "#{value}";

		String COMMAND_LIST = "#{commandList}";
		
		String DATE = "#{date}";
		
		//XXX send or receive
		
		String WRITE_DATA_EQUAL = "#writeData=";
		
		String WRITE_BASE_DATA_EQUAL = "#writeBaseData=";
		
		String READ_BASE_DATA_EQUAL = "#readBaseData=";
		
		String CLASS_JAVA_NAME = "#{classJavaName}";
		String CLASS_JAVA_NAME_1 = "#{classJavaName1}";
		String CLASS_JAVA_NAME_2 = "#{classJavaName2}";
		
		String PARAM_NAME = "#{paramName}";
		
		String UPPER_PARAM_NAME = "#{UpperParamName}";
		
		String PARAM = "#{param}";
		
		String PACKAGE = "#{package}";
		
		String PACKAGES = "#{packages}";
		
		String PACKAGE_NAME = "#{packageName}";
		
		String WRITE_DETAIL = "#{writeDetail}";
		
		String READ_DETAIL = "#{readDetail}";
		
		String READ_DATA_EQUAL = "#readData=";
		
		String ILIST_DATA = "#ilistData=";
		
		String IMPORT_PACKAGE_EQUAL = "#importPackage=";
		
		String LIST_NAME = "#{listName}";
		
		String U_LIST_NAME = "#{ListName}";
		
		String ENTITY_NAME = "#{entityName}";
		
		String ENTITY_NAME_1 = "#{entityName1}";
		
		String ENTITY_NAME_2 = "#{entityName2}";

		String LOOP_PARAM = "#{loopName}";
		
		String PARAM_1 = "#{param1}";
		String PARAM_2 = "#{param2}";
		
		String PARENT_LOOP_PARAM = "#{parentLoopName}";
		
		String FUNCTION_PARAM = "#{functionParam}";

		String VOLUATION_PARAM = "#{voluationParam}";
		
		String FUNCTION_EQUAL = "#function=";
		
		String CONTROLLER_DETAIL = "#{controllerDetail}";
		
		String NODE_EQUAL = "#node=";
		
		String NODES = "#{nodes}";
		
		String READ_PARAMS = "#{readParams}";
		
		String WRITE_PARAMS = "#{writeParams}";
		
		String PARENT_NAME = "#{parentName}";
		
		String KEY_VALUE = "#{KV}";

		String DECLARE_PARAMS = "#{declareParams}";
		
		String DECLARE_PARAMS_EQUAL = "#declareParams=";
		
		String ROW_DATA_EQUAL = "#rowData=";
		
		String LIST_DATA_EQUAL = "#listData=";
		
		String INNER_LIST_DATA_EQUAL = "#innerListData=";
		
		String INNER_BASE_LIST_DATA_EQUAL = "#innerBaseTypeListData=";
		
		String BASE_DATA_EQUAL = "#baseData=";
		
		String VOLUATION_PARAM_EQUAL = "#voluationParam=";
		
		String BASE_TYPE_LIST_DATA_EQUAL = "#baseTypeListData=";
		
		String BASE_TYPE_DATA_EQUAL = "#baseTypeData=";

		String MAP_TYPE_EQUAL = "#mapType=";
		
		String LIST_TYPE_EQUAL = "#listType=";
		
		//Map类型
		String BASE_TYPE_MAP_DATA_EQUAL = "#baseTypeMapData=";
		
		String INNER_BASE_TYPE_MAP_DATA_EQUAL = "#innerBaseTypeMapData=";
		
		String BASE_TYPE_ROW_MAP_DATA_EQUAL = "#baseTypeRowMapData=";
		
		String MAP_DATA_EQUAL = "#mapData=";
		
		String INNER_MAP_DATA_EQUAL = "#innerMapData=";
		
		String BASE_ROW_MAP_DATA_EQUAL = "#baseRowMapData=";
		
		//Array类型
		String BASE_TYPE_ARRAY_DATA_EQUAL = "#baseTypeArrayData=";
		
		String INNER_BASE_TYPE_ARRAY_DATA_EQUAL = "#innerBaseTypeArrayData=";
		
		String BASE_TYPE_ROW_ARRAY_DATA_EQUAL = "#baseTypeRowArrayData=";
		
		String ARRAY_DATA_EQUAL = "#arrayData=";
		
		String INNER_ARRAY_DATA_EQUAL = "#innerArrayData=";
		
		String BASE_ROW_ARRAY_DATA_EQUAL = "#baseRowArrayData=";
		
	}
	
	public interface CSSName {
		String FONT_14 = "font-14";
	}
	
}
