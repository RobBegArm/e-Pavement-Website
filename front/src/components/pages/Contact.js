import classes from "./Contact.module.css";

import { motion } from "framer-motion";

import MainSection from "../UI/MainSection";
import ContactForm from "../UI/ContactForm";
import BackToTopBtn from "../UI/BackToTopBtn";
import LoadedImage from "../UI/LoadedImage";
import Member from "../UI/Member";

const Contact = (props) => {
  document.title = "e-Pavement | Contact";
  document.querySelector(`link[rel="canonical"]`).href =
    "https://e-pavement.eu/contact";

  return (
    <motion.div
      className="animated--contact"
      initial={{ opacity: 0 }}
      animate={{ opacity: 1, transition: { duration: 0.2 } }}
      exit={{ opacity: 0, transition: { duration: 0 } }}
    >
      <MainSection
        name="contact"
        className={classes["section--contact"]}
        menuIsShown={props.menuIsShown}
      >
        <div className={`container ${classes["contact-headline"]} `}>
          <p>Interested?</p>
          <p>
            Feel free to fill out the form, and we will reach out to you as soon
            as possible!
          </p>
          <hr />
        </div>
        <ContactForm />
        <div className={`container ${classes["contact-next-events"]} `}>
          <hr />
          <p>You are welcome to visit us at the upcoming events!</p>
          <div
            className={`container ${classes["contact-next-events-logo-box"]} `}
          >
            <div className={classes["logo-image-box"]}>
              <a
                href={"https://itsworldcongress.com/"}
                target={"_blank"}
                rel={"noopener noreferrer"}
              >
                <LoadedImage
                  imgFolder={"contact"}
                  imgName={"itsworldcongresslogo"}
                  imgAlt={"Dubai ITS World Congress"}
                />
              </a>
            </div>
          </div>
          <hr />
        </div>
        <Member />
        <div className={`container ${classes["contact-footer"]} `}>
          <p>
            &#x69;&#x6E;&#x66;&#x6F;&#x40;&#x65;&#x2D;&#x70;&#x61;&#x76;&#x65;&#x6D;&#x65;&#x6E;&#x74;&#x2E;&#x65;&#x75;
          </p>
        </div>
        <BackToTopBtn />
      </MainSection>
    </motion.div>
  );
};

export default Contact;
