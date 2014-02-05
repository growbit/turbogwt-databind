Turbo GWT (*TurboG*) MVP Databind
==

**Turbo GWT** is a suite of libs, inspired by famous Javascript MVVM frameworks like Knockout.js and Angular.js, intended to speed up development of GWT applications grounded on the MVP pattern.

**Turbo GWT MVP Databind** is a databind micro-framework that helps you to keep your model and view updated while iteracting of both sides, remaining true to the roots of MVP.

## How is TurboG Databind different from other databinding frameworks?
* TurboG Databind is for MVP.
* TurboG Databind is focused on promoting a testable and decoupled design to your Presentation/UI codes.
* TurboG Databind is alwalys worried about Separation of Concerns and Single Responsibility concepts.
* TurboG Databind is pluggable.
* TurboG Databind is designed to be easily integrated into your existing project, adding the databind functionality with the least possible effort.
* TurboG Databind is in love with IOC. <3

## Features
* Decoupled Model-View synchronization through Presenter
* Validation
* Formatting

## Why a new databind framework?
* Editor Framework is verbose and complex.
* Editor Framework has a overstepped and non-intuitive workflow.

## How do I use it?
The databind is achieved by a 2-way binding. 
Both Presenter and View must bind its elements to a common id.

Suppose the following model:
```java
public class Person {

    private String name;
    private Date birthday;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}
```

1) The first step is to create a class to hold all functions necessary to configure your bindings. It's mandatory to create accessors for each field you'll bind, and you can additionally create validators and/or formatters. This design favors testing while promotes a good separation of concerns.
```java
public final class PersonProperties {

    /* ACCESSORS */

    public static final TextPropertyAccessor<Person> NAME_ACCESSOR = new TextPropertyAccessor<Person>() {
        @Override
        public void setValue(Person person, @Nullable String value) {
            person.setName(value);
        }

        @Nullable
        @Override
        public String getValue(Person person) {
            return person.getName();
        }
    };

    public static final DatePropertyAccessor<Person> BIRTHDAY_ACCESSOR = new DatePropertyAccessor<Person>() {
        @Override
        public void setValue(Person person, @Nullable Date value) {
            person.setBirthday(value);
        }

        @Nullable
        @Override
        public Date getValue(Person person) {
            return person.getBirthday();
        }
    };


    /* VALIDATORS */

    public static final Validator<Person, String> NAME_VALIDATOR =
            new RequiredValidator<Person, String>("The name is required.");

    public static final Validator<Person, Date> BIRTHDAY_VALIDATOR = new Validator<Person, Date>() {
        @Override
        public Validation validate(Person object, @Nullable Date value) {
            // First check if the value is not null
            if (value == null) {
                final ValidationMessage message =
                        new ValidationMessage("The birthday date is required.", ValidationMessage.Type.ERROR);
                return Validation.invalid(message);
            }

            // The person cannot be born in future
            if (value.after(new Date())) {
                final ValidationMessage message =
                        new ValidationMessage("The birthday date cannot be after today", ValidationMessage.Type.ERROR);
                return Validation.invalid(message);
            }
            
            return Validation.valid();
        }
    };
}
```

Note that you can easily test BIRTHDAY_VALIDATOR and check if it is behaving like expected.

2) Then, you set-up your Presenter like this:
```java
public class PersonPresenter {

    interface PersonView extends DatabindView, IsWidget {}

    private final Binding<Person> binding;

    public PersonPresenter(PersonView view) {
        // Initiate the binding with the view
        binding = new BindingImpl<Person>(view);

        // Bind the properties
        binding.bind("name", PersonProperties.NAME_ACCESSOR, PersonProperties.NAME_VALIDATOR);
        binding.bind("birthday", PersonProperties.BIRTHDAY_ACCESSOR, PersonProperties.BIRTHDAY_VALIDATOR);
    }
}
```

3) Finally, your View should look like this:
```java
public class PersonViewImpl extends DatabindViewImpl implements PersonPresenter.PersonView {

    private final TextBox name = new TextBox();
    private final DateBox birthday = new DateBox();

    public PersonViewImpl() {
        // Construct the View (you can do it via UiBinder too)
        FlowPanel panel = new FlowPanel();
        panel.add(name);
        panel.add(birthday);
        initWidget(panel);

        // Bind the widgtes
        bind("name", name, Strategy.ON_CHANGE);
        bind("birthday", birthday, Strategy.ON_CHANGE);
    }

    @Override
    public void onValidationFailure(String property, @Nullable ValidationMessage message) {
        // You can optionally handle any validation failures.
        // Usually you will notify the user about the failure and how to fix it.
    }

    @Override
    public void onValidationSuccess(String property, @Nullable ValidationMessage message) {
        // You can optionally handle any validation successes.
        // Usually you will clean any error message on display, or show some success/warning/info message.
    }
}
```

[*DatabindViewImpl*](https://github.com/growbit/turbogwt-databind/blob/master/src/main/java/org/turbogwt/mvp/databind/client/DatabindViewImpl.java) is simply an extension of Composite with databind support.

Now, anytime the user changes the value of name textbox or birthday datebox, the new value is automatically sent to the Presenter passing through a validation. The view is notified of the validation. If the validation succeeds, the value is set into the model.

# Downloads
TurboG will be available only in Maven Central.
## Maven
(Under construction)

# Documentation
## Javadocs
(Under construction)

## Related articles
* [Original MVP specification](http://martinfowler.com/eaaDev/uiArchs.html#Model-view-presentermvp)
* [MVP - Passive View](http://martinfowler.com/eaaDev/PassiveScreen.html)
* [Inversion of Control (IOC)](http://martinfowler.com/bliki/InversionOfControl.html)
 
# Extensions
* [TurboG Databind GWT-Platform Extension](http://github.com/growbit/turbogwt-gwtp)
