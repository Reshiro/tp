package seedu.innsync.logic.parser;

import static seedu.innsync.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.innsync.logic.parser.CliSyntax.PREFIX_REQUEST;

import seedu.innsync.commons.core.index.Index;
import seedu.innsync.logic.Messages;
import seedu.innsync.logic.commands.DeleteRequestCommand;
import seedu.innsync.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteRequestCommand object
 */
public class DeleteRequestCommandParser implements Parser<DeleteRequestCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteRequestCommand
     * and returns a DeleteRequestCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    @Override
    public DeleteRequestCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_REQUEST);
        if (!argMultimap.getValue(PREFIX_REQUEST).isPresent()) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteRequestCommand.MESSAGE_USAGE));
        }

        Index contactIndex;
        try {
            contactIndex = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(Messages.MESSAGE_PARSE_EXCEPTION,
                    pe.getMessage(), DeleteRequestCommand.MESSAGE_USAGE), pe);
        }

        Index requestIndex;
        try {
            requestIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_REQUEST).get());
        } catch (ParseException pe) {
            if (pe.getMessage().equals(ParserUtil.MESSAGE_INVALID_INDEX)) {
                throw new ParseException(String.format(Messages.MESSAGE_PARSE_EXCEPTION,
                    "Request index is not a non-zero unsigned integer.", DeleteRequestCommand.MESSAGE_USAGE), pe);
            }
            throw new ParseException(String.format(Messages.MESSAGE_PARSE_EXCEPTION,
                    pe.getMessage(), DeleteRequestCommand.MESSAGE_USAGE), pe);
        }

        return new DeleteRequestCommand(contactIndex, requestIndex);
    }
}
