package netWork.model;


public class CommandModel
{
  private int opcode;
  private CommandType type;
  private CommandInfoModel[] commandInfoModel;

  public CommandModel()
  {
  }

  public CommandModel(int opcode, String type)
  {
    this.opcode = opcode;

    if (CommonModuleConfig.STRING_IS_CLIENT.equalsIgnoreCase(type))
    {
      this.type = CommandType.CLIENT_TYPE;
    }
    else
    {
      this.type = CommandType.SERVER_TYPE;
    }
  }

  public int getOpcode()
  {
    return this.opcode;
  }

  public void setOpcode(int opcode)
  {
    this.opcode = opcode;
  }

  public CommandInfoModel[] getCommandInfoModel()
  {
    return this.commandInfoModel;
  }

  public void setCommandInfoModel(CommandInfoModel[] commandInfoModel)
  {
    this.commandInfoModel = commandInfoModel;
  }

  public CommandType getType() {
    return this.type;
  }

  public void setType(CommandType type) {
    this.type = type;
  }
}
