// const mongoose = require("mongoose");
import mongoose from "mongoose";

const DestinationSchema = new mongoose.Schema({
  id: Number,
  name: String,
  packageId: Number,
  activityIds: { type: [Number] },
});

const Destination = mongoose.model("Destination", DestinationSchema);
export default Destination;
