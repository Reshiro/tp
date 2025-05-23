package seedu.innsync.logic.parser;

import static seedu.innsync.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.innsync.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.innsync.commons.core.LogsCenter;
import seedu.innsync.logic.commands.AddCommand;
import seedu.innsync.logic.commands.CancelConfirmCommand;
import seedu.innsync.logic.commands.ClearCommand;
import seedu.innsync.logic.commands.Command;
import seedu.innsync.logic.commands.ConfirmCommand;
import seedu.innsync.logic.commands.DeleteCommand;
import seedu.innsync.logic.commands.DeleteRequestCommand;
import seedu.innsync.logic.commands.EditCommand;
import seedu.innsync.logic.commands.ExitCommand;
import seedu.innsync.logic.commands.FindCommand;
import seedu.innsync.logic.commands.HelpCommand;
import seedu.innsync.logic.commands.ListCommand;
import seedu.innsync.logic.commands.ListStarCommand;
import seedu.innsync.logic.commands.MarkRequestCommand;
import seedu.innsync.logic.commands.MemoCommand;
import seedu.innsync.logic.commands.RequestCommand;
import seedu.innsync.logic.commands.StarCommand;
import seedu.innsync.logic.commands.TagCommand;
import seedu.innsync.logic.commands.UndoCommand;
import seedu.innsync.logic.commands.UnmarkRequestCommand;
import seedu.innsync.logic.commands.UnstarCommand;
import seedu.innsync.logic.commands.UntagCommand;
import seedu.innsync.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class AddressBookParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    private static final Logger logger = LogsCenter.getLogger(AddressBookParser.class);
    /**
     * Current Command awaiting confirmation (if any)
     */
    private Command pendingCommand = null;
    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        if (pendingCommand != null) {
            return parseConfirmationCommand(userInput);
        }
        Command command = parseCommandFromInput(userInput);
        if (command.requireConfirmation()) {
            pendingCommand = command;
            return new ConfirmCommand();
        }
        return command;
    }

    /**
     * Parses user confirmation input into intended command result.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    private Command parseConfirmationCommand(String userInput) throws ParseException {
        boolean confirm = new ConfirmCommandParser().parse(userInput);
        if (!confirm) {
            pendingCommand = null;
            return new CancelConfirmCommand();
        }
        Command command = pendingCommand;
        pendingCommand = null;
        return command;
    }

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    private Command parseCommandFromInput(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");


        // Note to developers: Change the log level in config.json to enable lower level (i.e., FINE, FINER and lower)
        // log messages such as the one below.
        // Lower level log messages are used sparingly to minimize noise in the code.
        logger.fine("Command word: " + commandWord + "; Arguments: " + arguments);

        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case StarCommand.COMMAND_WORD:
            return new StarCommandParser().parse(arguments);

        case UnstarCommand.COMMAND_WORD:
            return new UnstarCommandParser().parse(arguments);

        case TagCommand.COMMAND_WORD:
            return new TagCommandParser().parse(arguments);

        case UntagCommand.COMMAND_WORD:
            return new UntagCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case ListStarCommand.COMMAND_WORD:
            return new ListStarCommand();

        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();

        case MemoCommand.COMMAND_WORD:
            return new MemoCommandParser().parse(arguments);

        case RequestCommand.COMMAND_WORD:
            return new RequestCommandParser().parse(arguments);

        case DeleteRequestCommand.COMMAND_WORD:
            return new DeleteRequestCommandParser().parse(arguments);

        case MarkRequestCommand.COMMAND_WORD:
            return new MarkRequestCommandParser().parse(arguments);

        case UnmarkRequestCommand.COMMAND_WORD:
            return new UnmarkRequestCommandParser().parse(arguments);

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        default:
            logger.finer("This user input caused a ParseException: " + userInput);
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
