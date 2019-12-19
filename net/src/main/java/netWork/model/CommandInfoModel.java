package netWork.model;


public class CommandInfoModel
{
  private String type;
  //private Resolve.BuffType typeId;
  private int size;
  private String name;
  private CommandInfoModel[] commandList;

  public CommandInfoModel(String name, String type, int size)
  {
    this.name = name;
    this.type = type;

    //this.typeId = Resolve.BuffType.getType(type);
    this.size = size;
  }

  public CommandInfoModel(String name, String type, int size, CommandInfoModel[] commandList)
  {
    this.name = name;
    this.type = type;

   // this.typeId = Resolve.BuffType.getType(type);
    this.size = size;
    this.commandList = commandList;
  }

  public String getType() {
    return this.type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public int getSize() {
    return this.size;
  }

  public void setSize(int size) {
    this.size = size;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

 /* public Resolve.BuffType getTypeId() {
    return this.typeId;
  }

  public void setTypeId(Resolve.BuffType typeId) {
    this.typeId = typeId;
  }*/

  public CommandInfoModel[] getCommandList() {
    return this.commandList;
  }

  public void setCommandList(CommandInfoModel[] commandList) {
    this.commandList = commandList;
  }
}