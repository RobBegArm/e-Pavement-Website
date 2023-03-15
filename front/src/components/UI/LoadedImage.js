import { useState, Fragment, useCallback } from "react";
import Spinner from "./Spinner";

const LoadedImage = (props) => {
  const [loaded, setLoaded] = useState(false);

  const onLoad = useCallback(() => {
    setLoaded(true);
  }, []);

  return (
    <Fragment>
      <picture
        id={`picture_${props.imgName}`}
        className={props?.pictureClasses}
      >
        {/* Load lightweight WEPB if browser supports */}
        <source
          srcSet={`${process.env.PUBLIC_URL}/assets/images/${props.imgFolder}/${props.imgName}.webp`}
          type={"image/webp"}
        />
        {/* Fallback jpg if browser doesnt support */}
        <source
          srcSet={`${process.env.PUBLIC_URL}/assets/images/${props.imgFolder}/${props.imgName}.jpg`}
          type={"image/jpg"}
        />
        <img
          style={{ display: loaded ? "block" : "hidden", width: "100%" }}
          id={`img_${props.imgName}`}
          onLoad={onLoad}
          key={props.imgName}
          src={`${process.env.PUBLIC_URL}/assets/images/${props.imgFolder}/${props.imgName}.jpg`}
          alt={props.imgAlt}
          loading={props.imgLazyLoad ? "lazy" : "eager"}
          className={`shadowed-card-2 ${props?.imgClasses}`}
        />
      </picture>
      {/* Displays spinner while image loads */}
      {!loaded && <Spinner imgName={props.imgName} imgAlt={props.imgAlt} />}
    </Fragment>
  );
};

export default LoadedImage;
