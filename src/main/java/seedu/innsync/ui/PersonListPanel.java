package seedu.innsync.ui;

// import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.Region;
// import seedu.innsync.commons.core.LogsCenter;
import seedu.innsync.model.person.Person;

import java.util.function.Consumer;

/**
 * Panel containing the list of persons.
 */
public class PersonListPanel extends UiPart<Region> {
    private static final String FXML = "PersonListPanel.fxml";
    // private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);

    @FXML
    private ListView<Person> personListView;

    private Consumer<Person> selectionConsumer;

    /**
     * Creates a {@code PersonListPanel} with the given {@code ObservableList}.
     */
    public PersonListPanel(ObservableList<Person> personList) {
        super(FXML);
        personListView.setItems(personList);
        personListView.setCellFactory(listView -> new PersonListViewCell());
        
        // Configure selection model to allow single selection
        MultipleSelectionModel<Person> selectionModel = personListView.getSelectionModel();
        selectionModel.setSelectionMode(SelectionMode.SINGLE);
        
        // Add listener for selection changes
        selectionModel.selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (selectionConsumer != null) {
                selectionConsumer.accept(newValue);
            }
        });
    }

    /**
     * Sets a consumer to be called when a person is selected.
     */
    public void setSelectionConsumer(Consumer<Person> consumer) {
        this.selectionConsumer = consumer;
    }

    /**
     * Programmatically selects the person at the given index.
     */
    public void selectPerson(int index) {
        if (index >= 0 && index < personListView.getItems().size()) {
            personListView.getSelectionModel().select(index);
        }
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class PersonListViewCell extends ListCell<Person> {
        @Override
        protected void updateItem(Person person, boolean empty) {
            super.updateItem(person, empty);

            if (empty || person == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new PersonCard(person, getIndex() + 1).getRoot());
            }
        }
    }

}
