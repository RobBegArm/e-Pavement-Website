import { motion } from "framer-motion";
import { RxDimensions } from "react-icons/rx";
import { TbTemperature } from "react-icons/tb";
import { MdCompress } from "react-icons/md";
import { FaRoad } from "react-icons/fa";

import classes from "./Product.module.css";
import MainSection from "../UI/MainSection";
import LoadedImage from "../UI/LoadedImage";
import Feature from "../UI/Feature";
import BackToTopBtn from "../UI/BackToTopBtn";

const Product = (props) => {
  document.title = "e-Pavement | Product";
  document.querySelector(`link[rel="canonical"]`).href =
    "https://e-pavement.eu/product";

  return (
    <motion.div
      className="animated--product"
      initial={{ opacity: 0 }}
      animate={{ opacity: 1, transition: { duration: 0.2 } }}
      exit={{ opacity: 0, transition: { duration: 0 } }}
    >
      <MainSection
        name="product"
        className={classes["section--product"]}
        menuIsShown={props.menuIsShown}
      >
        <h2>Our Product</h2>
        <article className={`${classes["product-box"]}`} id="article_product">
          <p className="description-text">
            The basis of our products is a <strong>„smart road brick“</strong>{" "}
            that contains LEDs, electronic compounds, and PV elements
            encapsulated into thermo-reactive polymer.
          </p>
          <div className={classes["product-image-box"]}>
            <LoadedImage
              imgFolder={"product"}
              imgName={"productimage"}
              imgAlt={
                "E-Pavement smart road brick with LED solutions that can be assembled into panels"
              }
            />
          </div>
          <p className="description-text">
            The e-bricks may be integrated into <strong>panels</strong>. Within
            PV elements, depending on solar radiation conditions, they can act{" "}
            <strong>autonomously</strong> or be joined into a{" "}
            <strong>grid</strong>. The e-panels can communicate with the rest of
            the traffic infrastructure by wires, WiFi, Bluetooth, or other RF
            channels.
          </p>
          <ul className={classes["features-list"]}>
            <Feature
              featureKey={"flexibleDimensions"}
              icon={<RxDimensions />}
              description={<p>Flexible Dimensions</p>}
            />
            <Feature
              featureKey={"maxPressure"}
              icon={<MdCompress />}
              description={
                <p>
                  Max Pressure:
                  <br /> 1000 kg/cm<sup>2</sup>
                </p>
              }
            />
            <Feature
              featureKey={"maxTemperature"}
              icon={<TbTemperature />}
              description={
                <p>
                  Max- Temperatures:
                  <br /> 70-90 ℃
                </p>
              }
            />
            <Feature
              featureKey={"frictionCoefficient"}
              icon={<FaRoad />}
              description={
                <p>
                  Surface Friction μ։
                  <br /> 0.45
                </p>
              }
            />
          </ul>
          <p className="description-text">
            The product may be used for <strong>light signals</strong> for
            traffic participants or <strong>adaptive illumination</strong> of
            walking roads.
          </p>
        </article>
        <BackToTopBtn />
      </MainSection>
    </motion.div>
  );
};

export default Product;
