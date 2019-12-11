package netWork.model;

import java.util.Map;

public final class ModuleModel
{
  Object obj;
  Map<Integer, MethodModel> map;
  Map<Integer, CommandModel> sendableMap;

  ModuleModel()
  {
  }

  public ModuleModel(Object obj, Map<Integer, MethodModel> map, Map<Integer, CommandModel> sendableMap)
  {
    this.obj = obj;

    this.map = map;

    this.sendableMap = sendableMap;
  }

  public Object getObj()
  {
    return this.obj;
  }

  final void setObj(Object obj)
  {
    this.obj = obj;
  }

  public Map<Integer, MethodModel> getMap()
  {
    return this.map;
  }

  public void setMap(Map<Integer, MethodModel> map)
  {
    this.map = map;
  }

  public MethodModel getMethodModel(int opcode)
  {
    return (MethodModel)this.map.get(Integer.valueOf(opcode));
  }

  public Map<Integer, CommandModel> getSendableMap() {
    return this.sendableMap;
  }

  public void setSendableMap(Map<Integer, CommandModel> sendableMap) {
    this.sendableMap = sendableMap;
  }

  public CommandModel getSendable(int opcode)
  {
    return (CommandModel)this.sendableMap.get(Integer.valueOf(opcode));
  }
}