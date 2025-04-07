package seedu.innsync.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.innsync.logic.parser.CliSyntax.PREFIX_REQUEST;
import static seedu.innsync.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.ArrayList;
import java.util.List;

import seedu.innsync.commons.core.index.Index;
import seedu.innsync.commons.util.ToStringBuilder;
import seedu.innsync.logic.Messages;
import seedu.innsync.logic.commands.exceptions.CommandException;
import seedu.innsync.model.Model;
import seedu.innsync.model.person.Person;
import seedu.innsync.model.request.Request;
import seedu.innsync.model.request.exceptions.DuplicateRequestException;

/**
 * Adds a request to a specific person in the system.
 * The person is identified using their displayed index in the person list.
 */

public class RequestCommand extends Command {

    public static final String COMMAND_WORD = "req";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds a request to the contact identified by the index number in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer) " + PREFIX_REQUEST + "REQUEST\n"
            + "Example: " + COMMAND_WORD + " 1 " + PREFIX_REQUEST + "Need a bottle of champagne every morning";

    public static final String MESSAGE_SUCCESS = String.format(Messages.MESSAGE_COMMAND_SUCCESS,
            "Add request", "Added request to %s!");
    public static final String MESSAGE_DUPLICATE_REQUEST = String.format(Messages.MESSAGE_DUPLICATE_FIELD,
            "request");
    public static final String MESSAGE_INVALID_REQUEST_INDEX = "Request index is not a non-zero unsigned integer.";


    private final Index index;
    private final List<Request> requests;

    /**
     * Creates a {@code RequestCommand} to add a request to the specified person.
     *
     * @param index The index of the person in the displayed person list.
     * @param requests The set of requests to be added to the person.
     */
    public RequestCommand(Index index, List<Request> requests) {
        requireNonNull(index);
        requireNonNull(requests);
        this.index = index;
        this.requests = requests;
    }

    /**
     * Executes the request command by adding the request to the specified person.
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

        Person personToEdit = lastShownList.get(this.index.getZeroBased());

        List<Request> modelRequests = new ArrayList<>();
        for (Request request : requests) {
            modelRequests.add(model.getRequestElseCreate(request));
        }
        Person editedPerson = addRequestsPerson(personToEdit, modelRequests);

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(String.format(MESSAGE_SUCCESS, editedPerson.getName()), editedPerson);
    }

    private Person addRequestsPerson(Person personToCopy, List<Request> modelRequests) throws CommandException {
        Person editedPerson = new Person(personToCopy);
        for (Request request : modelRequests) {
            try {
                editedPerson.addRequest(request);
            } catch (DuplicateRequestException e) {
                throw new CommandException(MESSAGE_DUPLICATE_REQUEST);
            }
        }
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
        if (!(other instanceof RequestCommand)) {
            return false;
        }
        RequestCommand otherRequest = (RequestCommand) other;
        return index.equals(otherRequest.index) && requests.equals(otherRequest.requests);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("request", requests.toString())
                .toString();
    }
}
