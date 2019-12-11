package netWork.model;


import java.lang.reflect.Method;

public class MethodModel
{
  final Method method;
  final CommandInfoModel[] commandInfoModel;
  final Object obj;

  public MethodModel(Object obj, Method method, CommandInfoModel[] commandInfoModel)
  {
    this.method = method;
    this.commandInfoModel = commandInfoModel;
    this.obj = obj;
  }

  public Method getMethod()
  {
    return this.method;
  }

  public CommandInfoModel[] getCommandInfoModel()
  {
    return this.commandInfoModel;
  }

  public void execute(Object obj, Object[] objects) throws Exception
  {
    this.method.invoke(obj, objects);
  }

  public Object getObj()
  {
    return this.obj;
  }
}