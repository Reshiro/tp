package seedu.innsync.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.innsync.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import javafx.collections.ObservableList;
import seedu.innsync.commons.core.index.Index;
import seedu.innsync.commons.util.ToStringBuilder;
import seedu.innsync.logic.Messages;
import seedu.innsync.logic.commands.exceptions.CommandException;
import seedu.innsync.model.Model;
import seedu.innsync.model.person.Person;

/**
 * Unstars a contact in the addressbook
 */
public class UnstarCommand extends Command {

    public static final String COMMAND_WORD = "unstar";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Unstars the contact identified by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";
    public static final String MESSAGE_SUCCESS = String.format(Messages.MESSAGE_COMMAND_SUCCESS,
            "Unstar", "The contact %s was unstarred!");
    public static final String MESSAGE_FAILURE = String.format(Messages.MESSAGE_COMMAND_FAILURE,
            "Unstar", "The contact %s was not starred!");

    private final Index index;

    /**
     * @param index of the person in the filtered person list to edit
     */
    public UnstarCommand(Index index) {
        requireNonNull(index);
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        ObservableList<Person> lastShownList = model.getPersonList();
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person person = lastShownList.get(this.index.getZeroBased());
        if (!person.getStarred()) {
            throw new CommandException(String.format(MESSAGE_FAILURE, person.getName()));
        }
        Person unstarredPerson = getUnstarredPerson(person);
        model.setPerson(person, unstarredPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_SUCCESS, unstarredPerson.getName()), unstarredPerson);
    }

    private Person getUnstarredPerson(Person personToCopy) {
        return new Person(personToCopy.getName(),
                personToCopy.getPhone(),
                personToCopy.getEmail(),
                personToCopy.getAddress(),
                personToCopy.getMemo(),
                personToCopy.getRequests(),
                personToCopy.getBookingTags(),
                personToCopy.getTags(),
                false);
    }

    @Override
    public boolean requireConfirmation() {
        return false;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UnstarCommand)) {
            return false;
        }

        UnstarCommand otherUnstarCommand = (UnstarCommand) other;
        return index.equals(otherUnstarCommand.index);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .toString();
    }
}
