package seedu.innsync.logic.parser;

import static seedu.innsync.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.innsync.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.innsync.logic.parser.CliSyntax.PREFIX_BOOKINGTAG;
import static seedu.innsync.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.innsync.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.innsync.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.innsync.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import seedu.innsync.logic.commands.AddCommand;
import seedu.innsync.logic.parser.exceptions.ParseException;
import seedu.innsync.model.person.Address;
import seedu.innsync.model.person.Email;
import seedu.innsync.model.person.Name;
import seedu.innsync.model.person.Person;
import seedu.innsync.model.person.Phone;
import seedu.innsync.model.tag.BookingTag;
import seedu.innsync.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(
                    args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
                    PREFIX_ADDRESS, PREFIX_BOOKINGTAG, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        // Verify no duplicate prefixes for the provided fields
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS);
        
        // Name is required
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        
        // Phone, email, and address are optional
        Optional<Phone> phoneOpt = argMultimap.getValue(PREFIX_PHONE)
                .map(phone -> {
                    try {
                        return ParserUtil.parsePhone(phone);
                    } catch (ParseException e) {
                        return null;
                    }
                });
        
        Optional<Email> emailOpt = argMultimap.getValue(PREFIX_EMAIL)
                .map(email -> {
                    try {
                        return ParserUtil.parseEmail(email);
                    } catch (ParseException e) {
                        return null;
                    }
                });
        
        Optional<Address> addressOpt = argMultimap.getValue(PREFIX_ADDRESS)
                .map(address -> {
                    try {
                        return ParserUtil.parseAddress(address);
                    } catch (ParseException e) {
                        return null;
                    }
                });
        
        Set<BookingTag> bookingTagList = ParserUtil.parseBookingTags(argMultimap.getAllValues(PREFIX_BOOKINGTAG));
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
        
        Person person = new Person(name, phoneOpt, emailOpt, addressOpt, bookingTagList, tagList);

        return new AddCommand(person);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
