# play-routes-wire

This is an ad-hoc and tiny macro project for [Play Framework](https://www.playframework.com/) with [MacWire](https://softwaremill.com/open-source/).
This macro automatically `wire`s all controllers for given generated routes classes.

## Motivation

If you use MacWire, you have to write as below:

```
// define controller
class GreeterController(greetingService: GreetingService,
                                  langs: Langs,
                                  cc: ControllerComponents) extends AbstractController(cc)

// define an application loader and a component
class GreetingApplicationLoader extends ApplicationLoader {
  def load(context: Context): Application = new GreetingComponents(context).application
}

class GreetingComponents(context: Context) extends BuiltInComponentsFromContext(context)
  with ServicesModule
  with AssetsComponents
  with I18nComponents
  with play.filters.HttpFiltersComponents {

  // wire *all* controllers
  lazy val greeterController = wire[GreeterController]

  // wire a router
  lazy val router: Router = new Routes(httpErrorHandler, greeterController, assets)
}
```

If you use Guice, what you have to do is only to insert `@Inject()` as below:

```
// ↓ Only this!
@Inject() class GreeterController(greetingService: GreetingService,
                                  langs: Langs,
                                  cc: ControllerComponents) extends AbstractController(cc)
```

It is unreasonable! Can you think the case your project has hundreds of controllers?

This macro helps to wire all controllers.

## Usage

- Make sure that all controllers are subclass of `ControllerHelpers`.
- Make sure that all depending services are found in the scope.

Write as below:

```
class GreetingComponents(context: Context) extends BuiltInComponentsFromContext(context)
  with ServicesModule
  with AssetsComponents
  with I18nComponents
  with play.filters.HttpFiltersComponents {

  lazy val router: Router = Macro.wireRoutes[Routes](httpErrorHandler, "/")
}
```

- Supporting Play versions: 2.6.x, 2.7.x, 2.8.x
- Supporting MacWire versions: 2.3.x
