package responses

import models.Spread

/*
{
  "error": [],
  "result": {
    "XDAOXETH": [[1470482497, "0.00999000", "0.01005000"]],
    "last": 1470487560
  }
}
 */
/**
  * @param name pair name.
  * @param data pair name. Array of array entries(time, bid, ask).
  * @param last id to be used as since when polling for new spread data.
  */
case class SpreadResponse(name: String, data: Seq[Spread], last: Double)