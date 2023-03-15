import classes from "./BackToTopBtn.module.css";
import { Fragment, useEffect, useState } from "react";

const BackToTopBtn = (props) => {
  const [btnVisible, setBtnVisible] = useState(false);

  useEffect(() => {
    window.addEventListener("scroll", () => {
      setBtnVisible(window.scrollY > 76);
    });
  }, []);

  const scrollUp = () => {
    window.scrollTo({
      top: 0,
      behavior: "smooth",
    });
  };

  return (
    <Fragment>
      {btnVisible ? (
        <button className={classes["backToTopBtn"]} onClick={scrollUp}>
          Back To Top
        </button>
      ) : (
        ""
      )}
    </Fragment>
  );
};

export default BackToTopBtn;
