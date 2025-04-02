package seedu.innsync.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.innsync.logic.parser.CliSyntax.PREFIX_MEMO;

import seedu.innsync.commons.core.index.Index;
import seedu.innsync.logic.commands.MemoCommand;
import seedu.innsync.logic.parser.exceptions.ParseException;
import seedu.innsync.model.person.Memo;

/**
 * Parses input arguments and creates a new MemoCommand object
 */
public class MemoCommandParser implements Parser<MemoCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the MemoCommand
     * and returns a MemoCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public MemoCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_MEMO);
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_MEMO);
        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format("%s\n%s", pe.getMessage(), MemoCommand.MESSAGE_USAGE), pe);
        }

        String memo = argMultimap.getValue(PREFIX_MEMO).orElse("");

        return new MemoCommand(index, new Memo(memo));
    }
}
