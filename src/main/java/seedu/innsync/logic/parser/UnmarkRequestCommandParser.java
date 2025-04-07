package seedu.innsync.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.innsync.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.innsync.logic.parser.CliSyntax.PREFIX_REQUEST;

import seedu.innsync.commons.core.index.Index;
import seedu.innsync.commons.exceptions.IllegalValueException;
import seedu.innsync.logic.Messages;
import seedu.innsync.logic.commands.UnmarkRequestCommand;
import seedu.innsync.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new MemoCommand object
 */
public class UnmarkRequestCommandParser implements Parser<UnmarkRequestCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the MemoCommand
     * and returns a MemoCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public UnmarkRequestCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_REQUEST);
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_REQUEST);
        if (!argMultimap.getValue(PREFIX_REQUEST).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnmarkRequestCommand.MESSAGE_USAGE));
        }
        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(Messages.MESSAGE_PARSE_EXCEPTION,
                    pe.getMessage(), UnmarkRequestCommand.MESSAGE_USAGE), pe);
        }

        Index requestIndex;
        try {
            requestIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_REQUEST).orElse("1"));
        } catch (IllegalValueException ive) {
            if (ive.getMessage().equals(ParserUtil.MESSAGE_INVALID_INDEX)) {
                throw new ParseException(String.format(Messages.MESSAGE_PARSE_EXCEPTION,
                    "Request index is not a non-zero unsigned integer.", UnmarkRequestCommand.MESSAGE_USAGE), ive);
            }
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    UnmarkRequestCommand.MESSAGE_USAGE), ive);
        }

        return new UnmarkRequestCommand(index, requestIndex);
    }
}
