import classes from "./Feature.module.css";

const Feature = (props) => {
  return (
    <li className={classes["feature"]} key={props.featureKey}>
      <div className={classes["icon-box"]}>{props.icon}</div>
      {props.description}
    </li>
  );
};

export default Feature;
