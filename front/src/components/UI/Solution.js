import classes from "./Solution.module.css";
import LoadedImage from "./LoadedImage";

const Solution = (props) => {
  return (
    <div className={classes["solution"]}>
      <h3>{props.headline}</h3>
      {props.description}
      <div className={classes["img-container"]}>
        <LoadedImage
          imgFolder={"solutions"}
          imgName={props.img1Name}
          imgAlt={props.img1Alt}
          imgLazyLoad={true}
        />
        <LoadedImage
          imgFolder={"solutions"}
          imgName={props.img2Name}
          imgAlt={props.img2Alt}
          imgLazyLoad={true}
        />
      </div>
    </div>
  );
};

export default Solution;
