package pl.matyjasiakm.volunteermanager.data.dao


data class Temperature(val Maximum: Map<String,String>, val Minimum: Map<String,String>) {
    fun getMinC(): Float {
        return ((Minimum["Value"]!!).toFloat() - 32) * 0.5556f
    }

    fun getMaxC(): Float {
        return ((Maximum["Value"]!!).toFloat() - 32) * 0.5556f
    }
}

data class WeatherInfo(val IconPhrase: String)

data class DailyForecast(
    val Date: String,
    val Temperature: Temperature,
    val Day: WeatherInfo,
    val Night: WeatherInfo
) {
    fun getOnlyDate(): String {
        return Date.substring(0, 10)
    }
}

data class DailyForecastList (val DailyForecasts:List<DailyForecast>)
