import classes from "./Member.module.css";
import LoadedImage from "./LoadedImage";

import { AiOutlineMail, AiOutlineLinkedin } from "react-icons/ai";
import { RiSuitcaseLine } from "react-icons/ri";

const Member = (props) => {
  return (
    <div className={classes["Member-box"]}>
      <div className={classes["Member-image-box"]}>
        <LoadedImage
          imgFolder={"contact"}
          imgName={"allanlahi"}
          imgAlt={"Member of Board - Allan Lahi"}
        />
      </div>
      <div className={classes["Member-description-box"]}>
        <p className={classes["Member-name"]}>Allan Lahi</p>
        <div className={classes["Member-description-line"]}>
          <RiSuitcaseLine />
          <p className={classes["Member-position"]}>Board Member</p>
        </div>
        <div className={classes["Member-description-line"]}>
          <AiOutlineMail />
          <address>
            <a href="mailto:info@e-pavement.eu">
              {" "}
              <p className={classes["Member-link"]}>
                &#x61;&#x6C;&#x6C;&#x61;&#x6E;&#x2E;&#x6C;&#x61;&#x68;&#x69;&#x40;&#x65;&#x2D;&#x70;&#x61;&#x76;&#x65;&#x6D;&#x65;&#x6E;&#x74;&#x2E;&#x65;&#x75;
              </p>
            </a>
          </address>
        </div>
        <div className={classes["Member-description-line"]}>
          <AiOutlineLinkedin />
          <address>
            <a
              href="https://www.linkedin.com/in/allan-lahi/"
              target={"_blank"}
              rel={"noreferrer"}
            >
              {" "}
              <p className={classes["Member-link"]}>LinkedIn</p>
            </a>
          </address>
        </div>
      </div>
    </div>
  );
};

export default Member;
