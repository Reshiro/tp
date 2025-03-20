package seedu.innsync.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.innsync.model.person.Person;

/**
 * Panel for displaying detailed information about a selected {@code Person}.
 */
public class PersonDetailPanel extends UiPart<Region> {

    private static final String FXML = "PersonDetailPanel.fxml";

    @FXML
    private Label nameLabel;
    
    @FXML
    private Label phoneLabel;
    
    @FXML
    private Label emailLabel;
    
    @FXML
    private Label addressLabel;
    
    @FXML
    private FlowPane detailTags;
    
    @FXML
    private FlowPane detailBookingTags;
    
    @FXML
    private ImageView detailStarIcon;
    
    @FXML
    private VBox placeholderBox;
    
    /**
     * Creates a {@code PersonDetailPanel} with a default placeholder.
     */
    public PersonDetailPanel() {
        super(FXML);
        showPlaceholder();
    }
    
    /**
     * Updates the panel with the details of the given person.
     */
    public void setPerson(Person person) {
        if (person == null) {
            showPlaceholder();
            return;
        }
        
        // Hide placeholder and show details
        placeholderBox.setVisible(false);
        placeholderBox.setManaged(false);
        
        // Set person details
        nameLabel.setText(person.getName().fullName);
        phoneLabel.setText(person.getPhone().value);
        emailLabel.setText(person.getEmail().value);
        addressLabel.setText(person.getAddress().value);
        detailStarIcon.setVisible(person.getStarred());
        
        // Clear previous tags
        detailTags.getChildren().clear();
        detailBookingTags.getChildren().clear();
        
        // Add tags
        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> {
                    Label tagLabel = new Label(tag.tagName);
                    tagLabel.getStyleClass().add("detail-tag");
                    detailTags.getChildren().add(tagLabel);
                });
        
        // Add booking tags
        person.getBookingTags().stream()
                .sorted(Comparator.comparing(bookingTag -> bookingTag.bookingTagName))
                .forEach(bookingTag -> {
                    Label bookingTagLabel = new Label(bookingTag.toPrettier());
                    bookingTagLabel.getStyleClass().add("detail-booking-tag");
                    detailBookingTags.getChildren().add(bookingTagLabel);
                });
    }
    
    /**
     * Shows the placeholder when no person is selected.
     */
    private void showPlaceholder() {
        nameLabel.setText("");
        phoneLabel.setText("");
        emailLabel.setText("");
        addressLabel.setText("");
        detailStarIcon.setVisible(false);
        detailTags.getChildren().clear();
        detailBookingTags.getChildren().clear();
        
        placeholderBox.setVisible(true);
        placeholderBox.setManaged(true);
    }
}
