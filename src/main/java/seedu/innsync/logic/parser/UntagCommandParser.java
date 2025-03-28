package seedu.innsync.logic.parser;

import static seedu.innsync.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.innsync.logic.parser.CliSyntax.PREFIX_BOOKINGTAG;
import static seedu.innsync.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.stream.Stream;

import seedu.innsync.commons.core.index.Index;
import seedu.innsync.logic.commands.UntagCommand;
import seedu.innsync.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new UntagCommand object
 */
public class UntagCommandParser implements Parser<UntagCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the TagCommand
     * and returns a TagCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public UntagCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_BOOKINGTAG, PREFIX_TAG);

        if (!atLeastOnePrefixPresent(argMultimap, PREFIX_BOOKINGTAG, PREFIX_TAG)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UntagCommand.MESSAGE_USAGE));
        }
        boolean hasBookingTag = argMultimap.getValue(PREFIX_BOOKINGTAG).isPresent();
        boolean hasTag = argMultimap.getValue(PREFIX_TAG).isPresent();
        if (hasBookingTag == hasTag) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UntagCommand.MESSAGE_USAGE));
        }
        Index index = ParserUtil.parseIndex(argMultimap.getPreamble());
        String bookingTag = argMultimap.getValue(PREFIX_BOOKINGTAG).orElse("");
        String tag = argMultimap.getValue(PREFIX_TAG).orElse("");

        return new UntagCommand(index, tag, bookingTag);
    }
    /**
     * Returns true if at least one of the prefixes is present in the {@code ArgumentMultimap}.
     */
    private static boolean atLeastOnePrefixPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
