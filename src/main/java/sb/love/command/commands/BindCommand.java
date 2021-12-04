package sb.love.command.commands;

import sb.love.command.Command;

// TODO
public class BindCommand extends Command {
    public BindCommand() {
        super(new String[]{"bind", "b"}, "bind <Module> <Key>");
    }

    @Override
    public void exec(String args) throws Exception {

    }
}
