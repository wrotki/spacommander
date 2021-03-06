package spatutorial.client.modules

import diode.data.Pot
import diode.react.ModelProxy
import japgolly.scalajs.react.vdom.prefix_<^.<
import japgolly.scalajs.react.{BackendScope, Callback, ReactComponentB, ReactElement}
import spatutorial.client.components.Bootstrap.{Button, CommonStyle, Panel}
import spatutorial.client.components._
import spatutorial.client.services._

object Rest {

  case class Props(proxy: ModelProxy[Pot[Rest]])

  case class State(request: String, response: String)

  class Backend($: BackendScope[Props, State]) {
    def mounted(props: Props) =
    // dispatch a message to refresh the todos, which will cause TodoStore to fetch todos from the server
      Callback.when(props.proxy().isEmpty)(props.proxy.dispatchCB(RefreshRest))

    def doNothing(request: Option[String]) = {
      Callback.log("Rest button pushed")
      $.modState(s => s.copy())
    }

    def render(p: Props, s: State) = {
      Panel(Panel.Props("Message of the day"),
        // render messages depending on the state of the Pot
        AceEditorGenerated(
          mode="javascript",
          theme="monokai",
          name="aceeditormf",
          fontSize=14,
          value="console.log(\"Hello, world!\");"
        )()
      )

//      val ret = SampleReactComponent(SampleReactComponentProps("Foohfah", "Boooflept"))
//      ret
    }
  }

  // create the React component for To Do management
  val component = ReactComponentB[Props]("TODO")
    .initialState(State("/foo", "{\"bar\": null}")) // initial state from TodoStore
    //.stateless
    .renderBackend[Backend]
    .componentDidMount(scope => scope.backend.mounted(scope.props))
    .build

  /** Returns a function compatible with router location system while using our own props */
  def apply(proxy: ModelProxy[Pot[Rest]]) = component(Props(proxy))
}
