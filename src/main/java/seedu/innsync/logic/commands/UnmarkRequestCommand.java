package seedu.innsync.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.innsync.logic.parser.CliSyntax.PREFIX_REQUEST;
import static seedu.innsync.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.innsync.commons.core.index.Index;
import seedu.innsync.commons.util.ToStringBuilder;
import seedu.innsync.logic.Messages;
import seedu.innsync.logic.commands.exceptions.CommandException;
import seedu.innsync.model.Model;
import seedu.innsync.model.person.Person;
import seedu.innsync.model.request.Request;

/**
 * Adds a request to a specific person in the system.
 * The person is identified using their displayed index in the person list.
 */

public class UnmarkRequestCommand extends Command {

    public static final String COMMAND_WORD = "unmark";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Unmarks a request of the contact identified by the index number "
            + "in the displayed person list from its completion status.\n"
            + "Parameters: INDEX (must be a positive integer) " + PREFIX_REQUEST
            + "REQUEST_INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 " + PREFIX_REQUEST + "1";
    public static final String MESSAGE_SUCCESS = String.format(
            Messages.MESSAGE_COMMAND_SUCCESS, "Unmark request", "%s has been unmarked!");
    public static final String MESSAGE_INVALID_REQUEST_INDEX = String.format(
            Messages.MESSAGE_INVALID_ITEM_INDEX, "request");
    public static final String MESSAGE_FAILURE = String.format(
            Messages.MESSAGE_COMMAND_FAILURE, "Unmark request", "The request %s is not marked!");

    private final Index index;
    private final Index requestIndex;

    /**
     * Creates a {@code RequestCommand} to add a request to the specified person.
     *
     * @param index The index of the person in the displayed person list.
     * @param requestIndex The index of the request in the displayed request list of the person.
     *
     */
    public UnmarkRequestCommand(Index index, Index requestIndex) {
        requireNonNull(index);
        this.index = index;
        this.requestIndex = requestIndex;
    }

    /**
     * Executes the mark request command by marking the specified request
     * of the specified person as complete.
     *
     * @param model The model which contains the list of persons.
     * @return A {@code CommandResult} indicating the outcome of the command.
     * @throws CommandException If the specified index is invalid (out of bounds).
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getPersonList();
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        Person person = lastShownList.get(this.index.getZeroBased());
        List<Request> requests = person.getRequests();
        if (this.requestIndex.getOneBased() > requests.size()) {
            throw new CommandException(MESSAGE_INVALID_REQUEST_INDEX);
        }
        Request request = requests.get(this.requestIndex.getZeroBased());
        if (!request.isCompleted()) {
            throw new CommandException(String.format(MESSAGE_FAILURE, request.getRequestName()));
        }
        Person editedPerson = createEditedPerson(model, person, request, requestIndex);
        model.setPerson(person, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_SUCCESS, request.getRequestName()), editedPerson);
    }

    private Person createEditedPerson(Model model, Person person, Request request, Index index) {
        Request unmarkedRequest = new Request(request);
        unmarkedRequest.markAsIncomplete();
        unmarkedRequest = model.getRequestElseCreate(unmarkedRequest);
        Person editedPerson = new Person(person);
        editedPerson.removeRequest(request);
        editedPerson.addRequest(unmarkedRequest, index.getZeroBased());
        return editedPerson;
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
        if (!(other instanceof UnmarkRequestCommand)) {
            return false;
        }
        UnmarkRequestCommand otherRequest = (UnmarkRequestCommand) other;
        return index.equals(otherRequest.index);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .toString();
    }
}
