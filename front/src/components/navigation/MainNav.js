import { useState } from "react";

import MainNavLink from "./MainNavLink";
import classes from "./MainNav.module.css";

const MainNav = (props) => {
  const [activeLinkIndex, setActiveLinkIndex] = useState(null);

  //Sets the active link (which was hovered on last)
  const onHoverInHandler = (newIndex) => {
    setActiveLinkIndex(newIndex);
  };

  // Returns Pattern position value depending on which menu link is active
  const getPatternPosition = () => {
    switch (activeLinkIndex) {
      case "1":
        return "0% 100%";
      case "2":
        return "0% 80%";
      case "3":
        return "0% 60%";
      case "4":
        return "0% 40%";
      case "5":
        return "0% 20%";
      default:
        return "0% 100%";
    }
  };

  return (
    <nav
      className={`${classes["main-navbar"]} ${classes["animate--menu-open"]} ${
        props.isShown ? "block" : "none"
      }`}
    >
      <ul className={classes["main-nav-list"]}>
        <MainNavLink
          to="/about"
          label="About Us"
          activeIndex="1"
          onHoverIn={onHoverInHandler}
          onLinkClick={props.onLinkClick}
          liKey={"link_about-us"}
        />
        <MainNavLink
          to="/product"
          label="Product"
          activeIndex="2"
          onHoverIn={onHoverInHandler}
          onLinkClick={props.onLinkClick}
          liKey={"link_product"}
        />
        <MainNavLink
          to="/solutions"
          label="Solutions"
          activeIndex="3"
          onHoverIn={onHoverInHandler}
          onLinkClick={props.onLinkClick}
          liKey={"link_solutions"}
        />
        <MainNavLink
          to="/contact"
          label="Contact"
          activeIndex="4"
          onHoverIn={onHoverInHandler}
          onLinkClick={props.onLinkClick}
          liKey={"link_contact"}
        />
        {/* <MainNavLink
            to="/media"
            label="Media"
            activeIndex="5"
            onHoverIn={onHoverInHandler}
            onLinkClick={props.onLinkClick}
            liKey={"link_media"}
          /> */}
      </ul>
      <div className={classes["background"]} />
      <div
        className={classes["background--pattern"]}
        style={{ backgroundPosition: getPatternPosition() }}
      />
    </nav>
  );
};

export default MainNav;
