import { motion } from "framer-motion";

import classes from "./PageNotFound.module.css";
import MainSection from "../UI/MainSection";

const PageNotFound = () => {
  document.title = "e-Pavement | Page Not Found";

  return (
    <motion.div
      className="animated--page-not-found"
      initial={{ opacity: 0 }}
      animate={{ opacity: 1, transition: { duration: 0.2 } }}
      exit={{ opacity: 0, transition: { duration: 0 } }}
    >
      <MainSection
        name="pagenotfound"
        className={classes["section--page-not-found"]}
      >
        <div className={classes["message-box"]}>
          <p>404: Page Not Found</p>
          <hr />
          <p>
            It seems you have reached a URL that does not exist, please use the
            menu or home buttons on the top to get back to the website
          </p>
        </div>
      </MainSection>
    </motion.div>
  );
};

export default PageNotFound;
