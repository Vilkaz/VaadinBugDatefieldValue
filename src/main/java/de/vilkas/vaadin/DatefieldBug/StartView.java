package de.vilkas.vaadin.DatefieldBug;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Button;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SpringUI
public class StartView extends UI {

    @Override
    protected void init(final VaadinRequest request) {

        DateField dateField = new DateField();

        dateField.addValueChangeListener(
            event ->
                dateField.setValue(event.getValue().with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY)))

        );

        Label dateFieldValue = new Label();
        Button showDatefieldValue = new Button("Show Datefield Value", e -> {
            LocalDate date = dateField.getValue();
            if (date != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MM yyyy");
                dateFieldValue.setValue(date.format(formatter));
            }
        });

        HorizontalLayout buttons = new HorizontalLayout(dateField, showDatefieldValue, dateFieldValue);
        Label
            instruction = new Label("Businesslogic : The Date always has to be set on sunday. Same Week but always sunday" +
                                    "\n1: Select NOT a sunday in different week - > The Datefield sets Date on sunday" +
                                    "\n2: Click on the Show Datefield Value -> the correct Date will be shown" +
                                    "\n3: Select NOT a Sunday in same Week as currently selected- > the Datefield does not sets the Date on sunday." +
                                    "\n4: Click on the Show Datefield Value -> the sunday of the selected week will will be show" +
                                    "\nThis means, the Value of Datefield is different, then the value on the Screen. The bug only works, if you stay in same week!" +
                                    "\nIf you change the week, the correct value will be displayed.",
            ContentMode.PREFORMATTED);

        VerticalLayout mainLayout = new VerticalLayout(buttons, instruction);
        setContent(mainLayout);
    }

}
