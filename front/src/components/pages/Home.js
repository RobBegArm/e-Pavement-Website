import React from "react";
import { useNavigate } from "react-router-dom";
import { motion } from "framer-motion";

import classes from "./Home.module.css";
import MainSection from "../UI/MainSection";

const Home = (props) => {
  document.title = "e-Pavement | Home";
  document.querySelector(`link[rel="canonical"]`).href =
    "https://e-pavement.eu/";

  let navigate = useNavigate();
  const routeChange = (url) => {
    navigate(url);
  };

  return (
    <motion.div
      className="animated--home"
      initial={{ opacity: 0 }}
      animate={{ opacity: 1, transition: { duration: 0.2 } }}
      exit={{ opacity: 0, transition: { duration: 0 } }}
    >
      <MainSection
        name="home3"
        className={classes["section--home"]}
        menuIsShown={props.menuIsShown}
      >
        <div className={classes["hero-container"]}>
          <div className={classes["hero-description"]}>
            <div className={classes["hero-headline-box"]}>
              <h1>Smart road safety.</h1>
            </div>
            <div className={classes["hero-secondary-headline-box"]}>
              <h2>
                The e-bricks enhance the safety of the <br /> most vulnerable
                traffic participants.
              </h2>
            </div>
            <div className={classes["cta-buttons-box"]}>
              <button
                className={`btn ${classes["btn--learn-more"]} 
            focusable`}
                onClick={() => routeChange("/solutions")}
              >
                Learn More
              </button>
              <button
                className={`btn ${classes["btn--cta"]} focusable`}
                onClick={() => routeChange("/contact")}
              >
                Find your smart solution
              </button>
            </div>
          </div>
        </div>
      </MainSection>
    </motion.div>
  );
};

export default Home;
