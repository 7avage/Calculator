import org.apache.commons.cli.*;

/**
 * Simple console calculator
 * User: bubu
 * Date: 7/14/13
 * Time: 6:08 PM
 */
public class Calc {
    enum Operation {
        PLUS("+"), MINUS("-"),DIVIDE("/"), MULTIPLY("*");
        private String command;

        Operation(String command) {
            this.command = command;
        }

        double eval(double arg1, double arg2) {
            double result = 0.0;
            switch(this) {
                case PLUS : result = arg1 + arg2; break;
                case MINUS : result = arg1 - arg2; break;
                case DIVIDE : result = arg1 / arg2; break;
                case MULTIPLY : result = arg1 * arg2; break;
                default : break;
            }
            return (double)Math.round(result * 10000) / 10000;
        }
    }

    public static void main(String[] args)
    {
        Options options = new Options();
        options.addOption("a", true, "<decimal number>");
        options.addOption("b", true, "<decimal number>");
        options.addOption("o", true, "<operations +|-|*|/>");
        options.addOption("help", false, "--help");

        HelpFormatter formatter = new HelpFormatter();
        CommandLineParser parser = new GnuParser();

        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption("a") && cmd.hasOption("b") && cmd.hasOption("o")) {
                double arg1 = Double.parseDouble(cmd.getOptionValue("a"));
                double arg2 = Double.parseDouble(cmd.getOptionValue("b"));

                String command = cmd.getOptionValue("o");
                Operation operation = null;
                for (Operation op : Operation.values()) {
                    if (op.command.equals(command)) {
                        operation = op;
                        break;
                    }
                }
                if (operation != null) {
                    if (arg2 == 0 && operation.equals(Operation.DIVIDE)) {
                        System.out.println("Division by zero!");
                    } else {
                        System.out.println(operation.eval(arg1, arg2));
                    }
                } else {
                    System.out.println("Invalid operation");
                }
            } else if (cmd.hasOption("help")) {
                formatter.printHelp("help", options);
            } else {
                System.out.println("Incorrect entered arguments. Example: -a 22.45 -b 33.6 -o /");
            }
        } catch(ParseException e) {
            System.err.println("Incorrect entered data. Example: -a 22.45 -b 33.6 -o /");
        }
    }
}
