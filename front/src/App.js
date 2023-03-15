import { Fragment, useState } from "react";
import { Route, Routes, useLocation } from "react-router-dom";

import { AnimatePresence } from "framer-motion";

import Header from "./components/layout/Header";
import Footer from "./components/layout/Footer";
import MainNav from "./components/navigation/MainNav";
import AboutUs from "./components/pages/AboutUs";
import Product from "./components/pages/Product";
import Solutions from "./components/pages/Solutions";
import Contact from "./components/pages/Contact";
import PageNotFound from "./components/pages/PageNotFound";
import Home from "./components/pages/Home";

function App() {
  const location = useLocation();
  const [menuIsShown, setMenuIsShown] = useState(false);

  const showMenuHandler = () => {
    setMenuIsShown(true);
  };

  const hideMenuHandler = () => {
    setMenuIsShown(false);
  };

  return (
    <Fragment>
      {/*Always visible header */}
      <Header
        menuIsShown={menuIsShown}
        onShowMenu={showMenuHandler}
        onHideMenu={hideMenuHandler}
      />
      <main>
        {/*Main Nav menu is visible, only while menuIsShown is TRUE */}
        <MainNav onLinkClick={hideMenuHandler} isShown={menuIsShown} />
        {/*Main part rendered */}
        <AnimatePresence>
          <Routes location={location} key={location.pathname}>
            <Route path="/" element={<Home menuIsShown={menuIsShown} />} />
            <Route
              path="/about"
              element={<AboutUs menuIsShown={menuIsShown} />}
            />
            <Route
              path="/product"
              element={<Product menuIsShown={menuIsShown} />}
            />
            <Route
              path="/solutions"
              element={<Solutions menuIsShown={menuIsShown} />}
            />
            <Route
              path="/contact"
              element={<Contact menuIsShown={menuIsShown} />}
            />
            <Route
              path="/*"
              element={<PageNotFound menuIsShown={menuIsShown} />}
            />
          </Routes>
        </AnimatePresence>
      </main>
      {/*Always visible footer */}
      <Footer />
    </Fragment>
  );
}

export default App;
