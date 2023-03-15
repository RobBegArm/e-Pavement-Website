import { motion } from "framer-motion";

import classes from "./AboutUs.module.css";
import MainSection from "../UI/MainSection";
import LoadedImage from "../UI/LoadedImage";
import BackToTopBtn from "../UI/BackToTopBtn";
import PartnerLogo from "../UI/PartnerLogo";

import { FaRegHandshake } from "react-icons/fa";
import { AiOutlineSafety, AiOutlineFundProjectionScreen } from "react-icons/ai";

const AboutUs = (props) => {
  document.title = "e-Pavement | About Us";
  document.querySelector(`link[rel="canonical"]`).href =
    "https://e-pavement.eu/about";

  return (
    <motion.div
      className="animated--about-us"
      initial={{ opacity: 0 }}
      animate={{ opacity: 1, transition: { duration: 0.2 } }}
      exit={{ opacity: 0, transition: { duration: 0 } }}
    >
      <MainSection
        name="about-us"
        className={classes["section--about-us"]}
        menuIsShown={props.menuIsShown}
      >
        <article
          className={`${classes["our-story-box"]} container grid grid--2-cols`}
        >
          <h2>Our Story</h2>
          <p className="description-text">
            The start-up company e-Pavement was born in 2018 as a TalTech
            University spin-off to commercialize solar road technology developed
            at the university. Our core team consists of experienced scientists
            and engineers familiar with electronics, physics, material science,
            road and safety, ICT, and mechanical engineering.
          </p>
          <p className="description-text">
            We started from the results of the Nordic e-pavement cooperation
            project. During that project, TalTech in cooperation with the
            Estonian Road Administration developed the photovoltaic cells
            integration solution into a road pavement. TalTech University also
            integrated LEDs and electronic compounds so the road brick could
            feed itself with the necessary energy.
          </p>
          <p className="description-text">
            Based on the heritage described above, we focus on practical
            applications, enhancing the safety of the most vulnerable traffic
            participants – pedestrians and bicyclists. ’The Solutions were
            present at ITS Europe 2019 in Hannover, ITS World 2019 in Singapore,
            and ITS World 2021 in Hamburg. On 2022 we presented the smart road
            bricks solution at Intertraffic in Amsterdam (in cooperation with
            Triple sign Design from Sweden).
          </p>
          <LoadedImage
            pictureClasses={classes["our-story-picture-1"]}
            imgFolder={"aboutus"}
            imgName={"aboutus2"}
            imgAlt={
              "E-Pavement Researchers developing smart road safety bricks"
            }
          />
          <LoadedImage
            pictureClasses={classes["our-story-picture-2"]}
            imgFolder={"aboutus"}
            imgName={"aboutus1"}
            imgAlt={
              "E-Pavement Engineers installing smart road safety solution"
            }
          />
        </article>
        <article className={classes["partners"]}>
          <h2>Our Partners</h2>
          <ul className={classes["collabs"]}>
            <li key={"project1"}>
              <div className={classes["icon-box"]}>
                <AiOutlineFundProjectionScreen />
              </div>
              <p className="description-text">
                Currently, we are running an innovation project for the next
                generation of intelligent road bricks, supported by ERDF and
                Enterprise Estonia.
              </p>
            </li>
            <li key={"project2"}>
              <div className={classes["icon-box"]}>
                <AiOutlineSafety />
              </div>
              <p className="description-text">
                In cooperation with Triplesign from Sweden Triplesign System we
                develop an integrated solution for pedestrian safety.
              </p>
            </li>
            <li key={"project3"}>
              <div className={classes["icon-box"]}>
                <FaRegHandshake />
              </div>
              <p className="description-text">
                Enterprise Estonia has supported our developments by grants.
              </p>
            </li>
          </ul>
          <ul className={classes["partner-logos-list"]}>
            <PartnerLogo
              imgName={"erdf"}
              imgAlt={"European Union European Regional Development Fund"}
              link={
                "https://commission.europa.eu/funding-tenders/find-funding/eu-funding-programmes/european-regional-development-fund-erdf_en"
              }
              title={"European Union European Regional Development Fund"}
            />
            <PartnerLogo
              imgName={"tallinn"}
              imgAlt={"Tallinn City"}
              link={"https://www.tallinn.ee/en"}
              title={"Tallinn City"}
            />
            <PartnerLogo
              imgName={"tkonsult"}
              imgAlt={"T-Konsult"}
              link={"https://t-konsult.ee/"}
              title={"T-Konsult"}
            />
            <PartnerLogo
              imgName={"5gsolar"}
              imgAlt={"5G SOLAR"}
              link={"https://5gsolar.taltech.ee/"}
              title={"5G SOLAR"}
            />
            <PartnerLogo
              imgName={"eas"}
              imgAlt={"Enterprise Estonia"}
              link={"https://eas.ee/en/"}
              title={"Enterprise Estonia"}
            />
            <PartnerLogo
              imgName={"its"}
              imgAlt={"ITS Estonia"}
              link={"https://its-estonia.com/"}
              title={"ITS Estonia"}
            />
            <PartnerLogo
              imgName={"nordic"}
              imgAlt={"Nordic Plus"}
              link={"https://itsnordicplus.com/"}
              title={"Nordic Plus"}
            />
            <PartnerLogo
              imgName={"ulemiste"}
              imgAlt={"Ülemiste City"}
              link={"https://www.ulemistecity.ee/en/"}
              title={"Ülemiste City"}
            />
            <PartnerLogo
              imgName={"viimsi"}
              imgAlt={"Viimsi Parish"}
              link={"https://www.viimsivald.ee/"}
              title={"Viimsi Parish"}
            />
            <PartnerLogo
              imgName={"triplesign"}
              imgAlt={"Triple Sign System"}
              link={"https://www.triplesign.com/"}
              title={"Triple Sign System"}
            />
          </ul>
        </article>
        <BackToTopBtn />
      </MainSection>
    </motion.div>
  );
};

export default AboutUs;
