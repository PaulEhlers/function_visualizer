package paulehlers.functionvisualizerbackend.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import paulehlers.kotlinexpressionparser.Environment
import paulehlers.kotlinexpressionparser.Evaluator
import paulehlers.kotlinexpressionparser.Parser

data class EvaluateRequestBody(
    val expression: String,
    val range: Range,
    val steps: Double = 1.0,
)

data class Range(
    val from: Double,
    val to: Double
)

data class FunctionResult(
    val x: Double,
    val y: Double,
)

@RestController
class EvaluatorController {

    @PostMapping("/evaluate")
    fun evaluate(@RequestBody body: EvaluateRequestBody) : List<FunctionResult> {
        require(body.steps > 0) { "Step size must be positive" }
        require(body.range.from <= body.range.to) { "Range 'from' must be less than or equal to 'to'" }

        val parser = Parser()
        val ast = parser.parse(body.expression)
        val functionEnv =  Environment()
        val evaluator = Evaluator(Environment(Environment.default().addParent(functionEnv)))

        val functionResults = mutableListOf<FunctionResult>()

        var i = body.range.from
        while(i <= body.range.to) {
            functionEnv.defineValue("x", i)

            val result = evaluator.evaluate(ast)

            functionResults.add(FunctionResult(
                x = i,
                y = result
            ))

            i += body.steps
        }

        return functionResults
    }
}