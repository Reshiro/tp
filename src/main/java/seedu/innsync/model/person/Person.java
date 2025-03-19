package seedu.innsync.model.person;

import static seedu.innsync.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.innsync.commons.util.ToStringBuilder;
import seedu.innsync.model.tag.BookingTag;
import seedu.innsync.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final Set<BookingTag> bookingTags = new HashSet<>();
    private final Set<Tag> tags = new HashSet<>();
    private final boolean starred;

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Set<BookingTag> bookingTags,
                  Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, bookingTags, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.bookingTags.addAll(bookingTags);
        this.tags.addAll(tags);
        this.starred = false;
    }

    /**
     * Name must be present and not null.
     * Phone, email, and address may be null, in which case they will be replaced with default values.
     * Every other field must be present and not null.
     */
    public Person(Name name, Optional<Phone> phoneOpt, Optional<Email> emailOpt, Optional<Address> addressOpt,
                 Set<BookingTag> bookingTags, Set<Tag> tags) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(bookingTags);
        Objects.requireNonNull(tags);
        this.name = name;
        this.phone = phoneOpt.orElse(new Phone("+65 9999 9999"));
        this.email = emailOpt.orElse(new Email("default@example.com"));
        this.address = addressOpt.orElse(new Address("No address provided"));
        this.bookingTags.addAll(bookingTags);
        this.tags.addAll(tags);
        this.starred = false;
    }

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Set<BookingTag> bookingTags,
                  Set<Tag> tags, boolean starred) {
        requireAllNonNull(name, phone, email, address, bookingTags, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.bookingTags.addAll(bookingTags);
        this.tags.addAll(tags);
        this.starred = starred;
    }

    /**
     * Name must be present and not null.
     * Phone, email, and address may be null, in which case they will be replaced with default values.
     * Every other field must be present and not null.
     */
    public Person(Name name, Optional<Phone> phoneOpt, Optional<Email> emailOpt, Optional<Address> addressOpt,
                 Set<BookingTag> bookingTags, Set<Tag> tags, boolean starred) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(bookingTags);
        Objects.requireNonNull(tags);
        this.name = name;
        this.phone = phoneOpt.orElse(new Phone("+65 9999 9999"));
        this.email = emailOpt.orElse(new Email("default@example.com"));
        this.address = addressOpt.orElse(new Address("No address provided"));
        this.bookingTags.addAll(bookingTags);
        this.tags.addAll(tags);
        this.starred = starred;
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public boolean getStarred() {
        return starred;
    }

    public Set<BookingTag> getBookingTags() {
        return Collections.unmodifiableSet(bookingTags);
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return name.equals(otherPerson.name)
                && phone.equals(otherPerson.phone)
                && email.equals(otherPerson.email)
                && address.equals(otherPerson.address)
                && starred == otherPerson.starred
                && bookingTags.equals(otherPerson.bookingTags)
                && tags.equals(otherPerson.tags);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, bookingTags, tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("bookingTags", bookingTags)
                .add("tags", tags)
                .add("starred", starred)
                .toString();
    }

}
