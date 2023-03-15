import { motion } from "framer-motion";

import classes from "./Solutions.module.css";
import MainSection from "../UI/MainSection";
import Solution from "../UI/Solution";
import BackToTopBtn from "../UI/BackToTopBtn";

const Solutions = (props) => {
  document.title = "e-Pavement | Solutions";
  document.querySelector(`link[rel="canonical"]`).href =
    "https://e-pavement.eu/solutions";

  return (
    <motion.div
      className="animated--solutions"
      initial={{ opacity: 0 }}
      animate={{ opacity: 1, transition: { duration: 0.2 } }}
      exit={{ opacity: 0, transition: { duration: 0 } }}
    >
      <MainSection
        name="solutions"
        className={classes["section--solutions"]}
        menuIsShown={props.menuIsShown}
      >
        <article className={`${classes["solutions"]} container`}>
          <h2>SMART CROSSWALK</h2>
          {/* Regulated pedestrian crossing */}
          <Solution
            headline={"Regulated pedestrian crossing"}
            description={
              <p className="description-text">
                Pedestrian traffic lights are duplicated on the road surface.
                They catch attention of mobile phone users. Color-blind people
                perceive the traffic light colors better.
              </p>
            }
            img1Name={"regulated1"}
            img1Alt={
              "Regulated e-pavement smart road safety crosswalk with LEDs solution in Tallinn red light"
            }
            img2Name={"regulated2"}
            img2Alt={
              "Regulated e-pavement smart road safety crosswalk with LEDs solution in Tallinn green light"
            }
          />
          {/* Regulated bicycle crossing */}
          <Solution
            headline={"Regulated bicycle crossing"}
            description={
              <p className="description-text">
                Bicycle and walking road crossings have become a safety
                challenge. Smart pavement solutions help to enhance safety for
                all road users.
              </p>
            }
            img1Name={"regulatedbicycle1"}
            img1Alt={
              "Regulated e-pavement smart bicycle safety crosswalk with LEDs solution in Tallinn red light"
            }
            img2Name={"regulatedbicycle2"}
            img2Alt={
              "Regulated e-pavement smart bicycle safety crosswalk with LEDs solution in Tallinn green light"
            }
          />
          <h2>ADAPTIVE ILLUMINATION</h2>
          {/* Safe Road Near Mektory */}
          <Solution
            headline={"Safer Road"}
            description={
              <p className="description-text">
                The solution with adaptive illumination enables safety with
                minimal light pollution.
              </p>
            }
            img1Name={"adaptive1"}
            img1Alt={
              "Safe adaptive illumination e-pavement road safety solution with LEDs near Mektory"
            }
            img2Name={"adaptive2"}
            img2Alt={
              "Safe adaptive illumination e-pavement road safety solution with LEDs"
            }
          />
          {/* Unregulated pedestrian crossing */}
          <Solution
            headline={"Unregulated pedestrian crossing"}
            description={
              <p className="description-text">
                The waiting area at the crossing captures the attention of the
                pedestrian and drivers alike.
              </p>
            }
            img1Name={"unregulated1"}
            img1Alt={
              "Unregulated e-pavement smart pedestrian safety crosswalk with LEDs solution in Tallinn white light"
            }
            img2Name={"unregulated2"}
            img2Alt={
              "Unregulated e-pavement smart pedestrian safety crosswalk with LEDs solution in Tallinn"
            }
          />
        </article>
        <BackToTopBtn />
      </MainSection>
    </motion.div>
  );
};

export default Solutions;
