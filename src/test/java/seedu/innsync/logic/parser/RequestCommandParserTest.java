package seedu.innsync.logic.parser;

import static seedu.innsync.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.innsync.logic.commands.CommandTestUtil.VALID_REQUEST_AMY;
import static seedu.innsync.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.innsync.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.innsync.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.innsync.logic.Messages;
import seedu.innsync.logic.commands.RequestCommand;
import seedu.innsync.model.request.Request;


public class RequestCommandParserTest {

    private RequestCommandParser parser = new RequestCommandParser();

    @Test
    public void parse_validArgs_returnsRequestCommand() {
        // Test valid request name, assuming the parser expects a "requestName"
        assertParseSuccess(parser, "1 r/" + VALID_REQUEST_AMY, new RequestCommand(INDEX_FIRST_PERSON,
                new ArrayList<>(Arrays.asList(new Request(VALID_REQUEST_AMY)))));
    }

    @Test
    public void parse_invalidIndex_throwsParseException() {
        assertParseFailure(parser, "-1 r/1", String.format(Messages.MESSAGE_PARSE_EXCEPTION,
                ParserUtil.MESSAGE_INVALID_INDEX, RequestCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // Test invalid arguments that don't follow the expected format for the request name
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, RequestCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_requestNameTooLong_throwsParseException() {
        // Test a request name that exceeds 170 characters
        String longRequestName = "a".repeat(171); // Create a string with 171 characters
        String expectedMessage = Request.MESSAGE_LENGTH;
        assertParseFailure(parser, "1 r/" + longRequestName, expectedMessage);
    }

    @Test
    public void parse_multipleRequestNames_throwsParseException() {
        // Test multiple request names or invalid input
        assertParseSuccess(parser, "1 r/requestName1 r/requestName2",
                new RequestCommand(INDEX_FIRST_PERSON,
                        new ArrayList<>(Arrays.asList(new Request("requestName1"), new Request("requestName2")))));
    }
}
