package seedu.innsync.logic.parser;

import static seedu.innsync.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.innsync.logic.Messages.MESSAGE_PARSE_EXCEPTION;
import static seedu.innsync.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.innsync.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.innsync.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.innsync.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.jupiter.api.Test;

import seedu.innsync.commons.core.index.Index;
import seedu.innsync.logic.commands.DeleteRequestCommand;
import seedu.innsync.logic.commands.RequestCommand;

public class DeleteRequestCommandParserTest {

    private DeleteRequestCommandParser parser = new DeleteRequestCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteRequestCommand() {
        // Valid contact index and request index
        Index contactIndex = INDEX_FIRST_PERSON;
        Index requestIndex = INDEX_FIRST_PERSON;
        assertParseSuccess(parser, "1 r/1", new DeleteRequestCommand(contactIndex, requestIndex));

        // Different valid indices
        contactIndex = INDEX_SECOND_PERSON;
        requestIndex = INDEX_SECOND_PERSON;
        assertParseSuccess(parser, "2 r/2", new DeleteRequestCommand(contactIndex, requestIndex));
    }

    @Test
    public void parse_invalidContactIndex_throwsParseException() {
        // Non-numeric contact index
        assertParseFailure(parser, "a r/1", String.format(MESSAGE_PARSE_EXCEPTION,
                ParserUtil.MESSAGE_INVALID_INDEX, DeleteRequestCommand.MESSAGE_USAGE));

        // Negative contact index
        assertParseFailure(parser, "-1 r/1", String.format(MESSAGE_PARSE_EXCEPTION,
                ParserUtil.MESSAGE_INVALID_INDEX, DeleteRequestCommand.MESSAGE_USAGE));

        // Zero contact index
        assertParseFailure(parser, "0 r/1", String.format(MESSAGE_PARSE_EXCEPTION,
                ParserUtil.MESSAGE_INVALID_INDEX, DeleteRequestCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidRequestIndex_throwsParseException() {
        // Non-numeric request index
        assertParseFailure(parser, "1 r/a", String.format(MESSAGE_PARSE_EXCEPTION,
                RequestCommand.MESSAGE_INVALID_REQUEST_INDEX, DeleteRequestCommand.MESSAGE_USAGE));

        // Negative request index
        assertParseFailure(parser, "1 r/-1", String.format(MESSAGE_PARSE_EXCEPTION,
                RequestCommand.MESSAGE_INVALID_REQUEST_INDEX, DeleteRequestCommand.MESSAGE_USAGE));

        // Zero request index
        assertParseFailure(parser, "1 r/0", String.format(MESSAGE_PARSE_EXCEPTION,
                RequestCommand.MESSAGE_INVALID_REQUEST_INDEX, DeleteRequestCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingRequestIndex_throwsParseException() {
        // No request index provided (missing r/ prefix)
        assertParseFailure(parser, "1", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteRequestCommand.MESSAGE_USAGE));

        // Empty request index
        assertParseFailure(parser, "1 r/", String.format(MESSAGE_PARSE_EXCEPTION,
                RequestCommand.MESSAGE_INVALID_REQUEST_INDEX, DeleteRequestCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingAllArgs_throwsParseException() {
        // Empty input
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteRequestCommand.MESSAGE_USAGE));
    }
}
