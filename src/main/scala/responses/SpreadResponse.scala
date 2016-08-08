package responses

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
  * @param pair_name pair name. Array of array entries(time, bid, ask).
  * @param last      id to be used as since when polling for new spread data.
  */
case class SpreadResponse(pair_name: Seq[Spread], last: Double)