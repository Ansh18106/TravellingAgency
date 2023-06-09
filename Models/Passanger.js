// const mongoose = require("mongoose");
import mongoose from "mongoose";

const PassangerSchema = new mongoose.Schema({
  passangerNumber: Number,
  Balance: Number,
  type: Number,
  name: String,
  activityIds: { type: [Number] },
});

const Passanger = mongoose.model("Passanger", PassangerSchema);
export default Passanger;
